<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="io.github.svanderburg.layout.model.*,io.github.svanderburg.layout.model.page.*, test.*"%>
<%
Application app = (Application)request.getAttribute("app");
Page currentPage = (Page)request.getAttribute("currentPage");
%>
<%@ taglib uri="http://svanderburg.github.io" prefix="layout" %>
<layout:index app="<%= app %>" currentPage="<%= currentPage %>" />
