package com.controller;

import java.io.IOException;


import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.beans.Product;
import com.model.DB;

public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = request.getParameter("page");
		
		if(page == null) {
			request.getRequestDispatcher("admin/login.jsp").forward(request, response);;
		}else {
			doPost(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String page = request.getParameter("page");
		
//Connect with admin login form (login.jsp)
		if(page.equals("admin-login-form")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			
			if(username.equals("admin") && password.equals("admin")) {
				request.getRequestDispatcher("admin/index.jsp").forward(request, response);

			}
			else {
				request.setAttribute("msg", "Invalid Crediantials");
				request.setAttribute("username", username);
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);

			}
		}

//Delete button in admin product list
		if(page.equals("delete")) {
			String id = request.getParameter("id");
			DB db = new DB();
			try {
				db.deleteProduct(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "Product deleted successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);

		}
	
////Home button in admin nav bar
		if(page.equals("index")) {
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
		
//Add product button in nav bar
		if(page.equals("addproduct")) {
			request.getRequestDispatcher("admin/addProduct.jsp").forward(request, response);
		}
		
//edit button in admin product list
		if(page.equals("edit")) {
			String id = request.getParameter("id");
			DB account = new DB();
			Product p = null;
			try {
				 p = account.fetchProduct(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("p", p);
			request.getRequestDispatcher("admin/editProduct.jsp").forward(request, response);
		}
		
//Connect with edit Product form (editProduct.jsp) 
		if(page.equals("edit_product")){
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String featured = request.getParameter("featured");
			
			Product p = new Product();
			
			p.setId(Integer.parseInt(id));
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			
			DB account = new DB();
			try {
				account.updateProduct(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Product details updated successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}

//Connect with add Product form (addProduct.jsp) 
		if(page.equals("add_product")){
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String featured = request.getParameter("featured");
			String image = request.getParameter("image");
			
			Product p = new Product();
			
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			p.setImage("img/"+image);
			
			DB account = new DB();
			try {
				account.addProduct(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Product added Successfully", "Info", JOptionPane.INFORMATION_MESSAGE);
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
	}

}
