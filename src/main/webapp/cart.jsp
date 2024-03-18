<%@ page import="com.rossypotentials.model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rossypotentials.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="com.rossypotentials.dataAccessObject.ProductDao" %>
<%@ page import="com.rossypotentials.util.ConnectionUtils" %>
<%@ page import="java.text.DecimalFormat" %><%--
  Created by IntelliJ IDEA.
  User: decagon
  Date: 12/03/2024
  Time: 10:17 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    User auth = (User) request.getSession().getAttribute("auth");
    if(auth != null){
        request.setAttribute("auth", auth);
    }
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null){
        ProductDao pDao = new ProductDao(ConnectionUtils.getConnection());
        cartProduct = pDao.getCartProducts(cart_list);
        double total = pDao.getTotalCartPrice(cart_list);
        request.setAttribute("cart_list", cart_list);
        request.setAttribute("total",total);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <%@include file="includes/head.jsp"%>
</head>
<body>
    <%@include file="includes/navbar.jsp"%>
    <div class="container my-3">
        <div class="d-flex py-3"><h3>Total Price: $ ${(total>0)?dcf.format(total):0} </h3> <a class="mx-3 btn btn-primary" href="cart-check-out">Check Out</a></div>
        <table class="table table-light">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Category</th>
                <th scope="col">Price</th>
                <th scope="col">Buy Now</th>
                <th scope="col">Cancel</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (cart_list != null) {
                    for (Cart c : cartProduct) {
            %>
            <tr>
                <td><%=c.getName()%></td>
                <td><%=c.getCategory()%></td>
                <td><%= dcf.format(c.getPrice())%></td>
                <td>
                    <form action="order-now" method="post" class="form-inline">
                        <input type="hidden" name="id" value="<%= c.getId()%>" class="form-input">
                        <div class="form-group d-flex justify-content-between">
                            <a class="btn bnt-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a>
                            <input type="text" name="quantity" class="form-control"  value="<%=c.getQuantity()%>" readonly>
                            <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>
                        </div>
                      <a href="orders.jsp">  <button type="submit" class="btn btn-primary btn-sm">Buy</button></a>
                    </form>
                </td>
                <td><a href="remove-from-cart?id=<%=c.getId() %>" class="btn btn-sm btn-danger">Remove</a></td>
            </tr>

            <%
                    }}%>
            </tbody>
        </table>
    </div>
    <%@include file="includes/footer.jsp"%>
</body>
</html>
