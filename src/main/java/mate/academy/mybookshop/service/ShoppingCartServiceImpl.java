package mate.academy.mybookshop.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.ShoppingCartResponseDto;
import mate.academy.mybookshop.dto.cartitem.UpdateCartItemQuantityRequestDto;
import mate.academy.mybookshop.entity.BookEntity;
import mate.academy.mybookshop.entity.CartItemEntity;
import mate.academy.mybookshop.entity.ShoppingCartEntity;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.exception.EntityNotFoundException;
import mate.academy.mybookshop.mapper.CartItemMapper;
import mate.academy.mybookshop.mapper.ShoppingCartMapper;
import mate.academy.mybookshop.repository.BookRepository;
import mate.academy.mybookshop.repository.CartItemRepository;
import mate.academy.mybookshop.repository.ShoppingCartRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto retrieveUserShoppingCart(UserEntity user) {
        return shoppingCartMapper.toDto(getUserShoppingCart(user));
    }

    @Override
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            CreateCartItemRequestDto requestDto, UserEntity user) {
        ShoppingCartEntity shoppingCart = getUserShoppingCart(user);
        verifyCartOwnership(user, shoppingCart);

        BookEntity book = bookRepository.findById(requestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id " + requestDto.bookId() + " not exist"));

        CartItemEntity existingCartItem = cartItemRepository
                .findByShoppingCartAndBook(shoppingCart, book);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(requestDto.quantity());
            cartItemRepository.save(existingCartItem);
        } else {
            CartItemEntity cartItemEntity = cartItemMapper.toEntity(requestDto);
            cartItemEntity.setShoppingCart(shoppingCart);
            cartItemEntity.setBook(book);
            cartItemRepository.save(cartItemEntity);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItemQuantity(
            Long cartItemId,
            UpdateCartItemQuantityRequestDto requestDto,
            UserEntity user) {
        ShoppingCartEntity shoppingCart = getUserShoppingCart(user);
        verifyCartOwnership(user, shoppingCart);

        CartItemEntity cartItemEntity = getUserCartItem(cartItemId);

        cartItemEntity.setQuantity(cartItemMapper.toEntity(requestDto).getQuantity());
        cartItemRepository.save(cartItemEntity);

        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto deleteCartItem(Long cartItemId, UserEntity user) {
        Long userId = user.getId();
        ShoppingCartEntity shoppingCart = getUserShoppingCart(user);
        verifyCartOwnership(user, shoppingCart);

        CartItemEntity cartItemEntity = getUserCartItem(cartItemId);

        cartItemRepository.delete(cartItemEntity);
        Set<CartItemEntity> cartItems =
                cartItemRepository.findCartItemEntitiesByShoppingCartId(userId);

        shoppingCart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void createShoppingCart(UserEntity user) {
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    private void verifyCartOwnership(UserEntity user, ShoppingCartEntity shoppingCart) {
        if (!shoppingCart.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("User does not own this shopping cart");
        }
    }

    private ShoppingCartEntity getUserShoppingCart(UserEntity user) {
        return shoppingCartRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User shopping cart not found"));
    }

    private CartItemEntity getUserCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cart item with id " + cartItemId + " not exist"));
    }
}
