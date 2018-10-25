var cart = [];
var cartItem = function (productId, productName, quantity, price,imageUrl) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
    this.imageUrl = imageUrl;
};

$(document).ready(function () {
    loadShoppingCartForSubMenu();
    loadCartItems();
    loadCartItemForCheckout();
    $(".btn-add-to-cart").click(function () {
        var item = $(this);
        var productId = parseInt(item.attr("proid"));
        var productName = item.attr("proname");
        var quantity = parseInt(item.attr("quantity"));
        var price = parseFloat(item.attr("price"));
        var imageUrl = item.attr("proimg");

        addItemToCart(productId, productName, quantity, price,imageUrl);
        loadShoppingCartForSubMenu();
    });

    $("#subMenuCart").on("click", ".delete-cart-item", function () {
        var productId = $(this).attr("proid");
        removeAllCartItem(productId);
        loadShoppingCartForSubMenu();
    });


    $("#add-item-form").on("click", "#add-to-cart", function () {
        var item = $(this);
        var productId = parseInt(item.attr("proid"));
        var productName = item.attr("proname");
        var price = parseFloat(item.attr("price"));
        var quantity = parseInt($("#myQuantity").val());
        var imageUrl = item.attr("proimg");

        addItemToCart(productId, productName, quantity, price,imageUrl);
        loadShoppingCartForSubMenu();
    });
    
    $("#cart-content").on("click",".delete-item",function (){
        var item = $(this);
        var productId = parseInt(item.attr("proid"));
        removeAllCartItem(productId);
        loadShoppingCartForSubMenu();
        loadCartItems();       
    });
    
    $("#btnPayment").on("click",this,function (){
        Checkout();
    });
});

//add to cart
function addItemToCart(productId, productName, quantity, price,imageUrl) {
    for (var i in cart) {
        if (cart[i].productId === parseInt(productId)) {
            cart[i].quantity += quantity;
            saveCart();
            return;
        }
    }

    var item = new cartItem(productId, productName, quantity, price,imageUrl);
    cart.push(item);
    saveCart();
}

//down quantity of item in cart
function removeCartItem(productId) {
    for (var i in cart) {
        if (cart[i].productId === parseInt(productId)) {
            cart[i].quantity--;
            if (cart[i].quantity === 0) {
                cart.splice(i, 1);
            }
            break;
        }
    }
    saveCart();
}

//remove one item in cart 
function removeAllCartItem(productId) {
    for (var i in cart) {
        if (cart[i].productId === parseInt(productId)) {
            cart.splice(i, 1);
        }
    }
    saveCart();
}

//clear cart 
function emptyCart() {
    cart = [];
    saveCart();
}

//count quantity from cart
function countQuantity() {
    var sum = 0;
    for (var i in cart) {
        sum += cart[i].quantity;
    }
    return sum;
}

//calculate total ò cart
function sumTotal() {
    var total = 0.0;
    for (var i in cart) {
        total += cart[i].quantity * cart[i].price;
    }
    return total.toLocaleString("en");
}

function listCart() {
    var cartCoppy = [];
    for (var i in cart) {
        var item = cart[i];
        var itemCoppy = {};
        for (var p in item) {
            itemCoppy[p] = item[p];
        }
        cartCoppy.push(itemCoppy);
    }
    return cartCoppy;
}

function saveCart() {
    localStorage.setItem("shoppingCart", JSON.stringify(cart));
}

function getShoppingCart() {
    cart = JSON.parse(localStorage.getItem("shoppingCart"));
    if (cart === null) {
        cart = [];
        return cart;
    }
    return cart;
}

function loadShoppingCartForSubMenu() {
    var shoppingCart = getShoppingCart();
    var total = 0;
    var totalQuantity = 0;
    if (shoppingCart.length === 0) {
        $("#subMenuCart").html("");
        $("#subMenuCart").append('<div class="cart_empty">Giỏ hàng của bạn vẫn chưa có sản phẩm nào.</div>');
        $("#totalQuantity").text("(0)");
        $(".cart_price").text("0₫");
    } 
    else {
        $("#subMenuCart").html("");
        var cartElement = "<div class='cart_box_wrap'>";
        for (var i in shoppingCart) {
            total += shoppingCart[i].quantity * shoppingCart[i].price;
            totalQuantity += shoppingCart[i].quantity;
            cartElement += '<div class="cart_item clearfix">'
                    + '<div class="cart_item_image">'
                    + '<img src="' + shoppingCart[i].imageUrl + '"/>'
                    + '</div>'
                    + '<div class="cart_item_info">'
                    + '<p class="cart_item_title">'
                    + '<a proId="' + shoppingCart[i].productId + '">' + shoppingCart[i].productName + '</a>'
                    + '</p>'
                    + '<span class="cart_item_quantity">'
                    + '<input disabled="true" value="' + shoppingCart[i].quantity + '" class="quantity_top_cart"/>'
                    + '</span>'
                    + '<span class="cart_item_price">' + (shoppingCart[i].quantity * shoppingCart[i].price).toLocaleString("en") + ' ₫</span>'
                    + '<button style="margin-top: 25px;" class="btn btn-danger remove delete-cart-item" proid="' + shoppingCart[i].productId + '"><span><i class="fa fa-times"></i></span></button>'
                    + '</div>'
                    + '</div>';
        }
        cartElement += '</div>'
                + '<div class="total_cart">'
                + '<span>Tổng tiền: </span>'
                + '<span class="total_price pull-right">' + (shoppingCart[i].quantity * shoppingCart[i].price).toLocaleString('en') + ' ₫</span>'
                + '</div>'
                + '<div class="cart-buttons clearfix">'
                + '<a href="/users/order/" class="btn-cart">Xem giỏ hàng</a>'
                + '<a href="/users/order/payment" class="btn-check-out">Thanh toán</a>'
                + '</div>';
        $("#subMenuCart").append(cartElement);

        $(".total_price").html(total.toLocaleString("en") + "₫");
        $(".cart_price").html(total.toLocaleString("en") + "₫");
        $("#totalQuantity").html("(" + totalQuantity + ")");
    }
}

function loadCartItems(){
    var shoppingCart = getShoppingCart();
    var total = 0;
    
    if (shoppingCart.length === 0) {
        $("#cart-content").html("");
    } 
    else{
        $("#cart-content").html("");
        var cartElement = '';
        for (var i in shoppingCart) {
            total += shoppingCart[i].quantity * shoppingCart[i].price;
            cartElement += '<tr class="ng-scope">' 
                    + '<td class="image">' 
                    + '<a href="/users/products/detail/' + shoppingCart[i].productId + '">' 
                    + '<img class="img-responsive" src="'+ shoppingCart[i].imageUrl +'">'
                    + '</a>'
                    + '</td>' 
                    + '<td class="des">' 
                    + '<a href="javascript:void(0)" class="ng-binding">'+ shoppingCart[i].productName +'</a>'
                    + '</td>' 
                    + '<td class="price" price="' + shoppingCart[i].price + '">'+ shoppingCart[i].price.toLocaleString('en') +'</td>' 
                    + '<td class="quantity">' 
                    + '<input type="text" value="'+ shoppingCart[i].quantity +'" disabled="true" class="itemQuantity text"/>'
                    + '</td>' 
                    + '<td class="amount">'+ (shoppingCart[i].quantity * shoppingCart[i].price).toLocaleString('en') +' ₫</td>'
                    + '<td proid="' + shoppingCart[i].productId + '" class="delete-item">' 
                    + '<a href="javascript:void(0)"><i class="fa fa-trash"></i></a>'
                    + '</td>'
                    + '</tr>';
        }
        
        $("#cart-content").append(cartElement);
        $(".pay-price").html(total.toLocaleString('en'));
//        $(".itemQuantity").click(function (){
//           var itemQuantity = $(this);
//           var quantity = parseInt(itemQuantity.val());
//           var price =parseFloat($(".price").attr("price"));          
//           var subTotal = (quantity * price).toLocaleString('en');
//           $(".amount").html(subTotal + " ₫");
//           //console.log(quantity);
//        });
    }
}

function loadCartItemForCheckout(){
    var shoppingCart = getShoppingCart();
    var total = 0;
    
    if (shoppingCart.length === 0) {
        $(".cart-items").html("");
    }
    else{
        $(".cart-items").html("");
        var cartElement = '';
        for (var i in shoppingCart) {
            total += shoppingCart[i].quantity * shoppingCart[i].price;
            cartElement += '<div class="cart-item clearfix">'
                    + '<span class="image pull-left" style="margin-right:10px;">'
                    + '<a href="javascript:void(0)">' 
                    + '<img src="'+ shoppingCart[i].imageUrl + '" class="img-responsive">'
                    + '</a>'
                    + '</span>'
                    + '<div class="product-info pull-left">'
                    + '<span class="product-name">' 
                    + '<a href="javascript:void(0)">'+ shoppingCart[i].productName +'</a> x <span>' + shoppingCart[i].quantity + '</span>'
                    + '</span>'
                    + '</div>'
                    + '<span class="price ng-binding">'+ (shoppingCart[i].price * shoppingCart[i].quantity).toLocaleString('en') +' ₫</span>'
                    + '</div>';
        }
        $(".cart-items").append(cartElement);
        $(".totalMoney").html(total.toLocaleString('en') + ' ₫');
    }
}

function Checkout(){
    var shoppingCart = getShoppingCart();
    var cartData = "cartData=" + JSON.stringify(shoppingCart);
    $.ajax({   
        type: 'POST',
        contentType : 'application/json; charset=utf-8',  
        dataType: 'json',
        url: "/order/paymentSubmit",              
        data: cartData,       
        success: function (respone) {
            if(respone === 'OK'){
                emptyCart();
                alert(respone);
            }
            else{
                console.log(respone);
            }
        },
        error: function (e){
            console.log(e);
        },
        done : function (){
            console.log("DONE");
        }
    });
}


