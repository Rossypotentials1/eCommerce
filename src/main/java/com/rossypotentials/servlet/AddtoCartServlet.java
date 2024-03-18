package com.rossypotentials.servlet;


import com.rossypotentials.model.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/add-to-cart")
public class AddtoCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       response.setContentType("text/html;charset-UTF-8");
       try(PrintWriter out  = response.getWriter()){
           ArrayList<Cart> cartList = new ArrayList<>();
           int id = Integer.parseInt(request.getParameter("id"));
           Cart cm = new Cart();
           cm.setId(id);
           cm.setQuantity(1);

           HttpSession session = request.getSession();
           ArrayList<Cart> cart_List = (ArrayList<Cart>) session.getAttribute("cart-list");

           if (cart_List == null){
               cartList.add(cm);
               session.setAttribute("cart-list",cartList);
               response.sendRedirect("product_page.jsp");
           }
           else{
               cartList = cart_List;
               boolean exist = false;
               for (Cart c : cart_List){
                   if (c.getId() == id){
                       exist = true;
                       out.println("<h3 Style='color:crimson; text-align:center'> Item already exist in Cart.<a href='cart.jsp'>Go to Cart Page</a></h3>");
                   }

               }
               if (!exist){
                   cartList.add(cm);
                   response.sendRedirect("product_page.jsp");

               }
           }
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}
