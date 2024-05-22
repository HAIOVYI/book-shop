package mate.academy.mybookshop.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.mybookshop.dto.cartitem.CreateCartItemRequestDto;
import mate.academy.mybookshop.dto.cartitem.ShoppingCartResponseDto;
import mate.academy.mybookshop.dto.cartitem.UpdateCartItemQuantityRequestDto;
import mate.academy.mybookshop.entity.CartItemEntity;
import mate.academy.mybookshop.entity.ShoppingCartEntity;
import mate.academy.mybookshop.entity.UserEntity;
import mate.academy.mybookshop.mapper.CartItemMapper;
import mate.academy.mybookshop.mapper.ShoppingCartMapper;
import mate.academy.mybookshop.repository.CartItemRepository;
import mate.academy.mybookshop.repository.ShoppingCartRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto retrieveUserShoppingCart(UserEntity user, Pageable pageable) {
        Long id = user.getId();
        ShoppingCartEntity shoppingCart = shoppingCartRepository.getReferenceById(id);
        verifyCartOwnership(user, shoppingCart);
        Set<CartItemEntity> cartItems = cartItemRepository.findCartItemEntitiesByShoppingCartId(id);
        shoppingCart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addCartItemToShoppingCart(
            CreateCartItemRequestDto requestDto, UserEntity user) {
        Long id = user.getId();
        ShoppingCartEntity shoppingCart = shoppingCartRepository.getReferenceById(id);
        verifyCartOwnership(user, shoppingCart);

        CartItemEntity cartItemEntity = cartItemMapper.toEntity(requestDto);
        cartItemEntity.setShoppingCart(shoppingCartRepository.getReferenceById(id));
        CartItemEntity savedEntity = cartItemRepository.save(cartItemEntity);

        Set<CartItemEntity> cartItems = cartItemRepository.findCartItemEntitiesByShoppingCartId(id);
        cartItems.add(savedEntity);
        shoppingCart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateCartItemQuantity(
            Long cartItemId,
            UpdateCartItemQuantityRequestDto requestDto,
            UserEntity user) {
        Long id = user.getId();
        ShoppingCartEntity shoppingCart = shoppingCartRepository.getReferenceById(id);
        verifyCartOwnership(user, shoppingCart);

        CartItemEntity cartItemEntity = cartItemRepository.getReferenceById(cartItemId);
        cartItemEntity.setQuantity(cartItemMapper.toEntity(requestDto).getQuantity());
        cartItemRepository.save(cartItemEntity);

        Set<CartItemEntity> cartItems = cartItemRepository.findCartItemEntitiesByShoppingCartId(id);
        shoppingCart.setCartItems(cartItems);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto deleteCartItem(Long cartItemId, UserEntity user) {
        Long userId = user.getId();
        ShoppingCartEntity shoppingCart = shoppingCartRepository.getReferenceById(userId);
        verifyCartOwnership(user, shoppingCart);

        CartItemEntity cartItemEntity = cartItemRepository.getReferenceById(cartItemId);
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
}
