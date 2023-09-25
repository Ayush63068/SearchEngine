package com.Accio;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {

    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword=request.getParameter("Keyword");
        Connection connection= DatabaseConnection.getConnection();
        try {
            // for history servlet
            PreparedStatement preparedStatement= connection.prepareStatement("insert into history values(?,?)");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngine/Search?Keyword="+keyword);
            preparedStatement.executeUpdate();
            // for search servlet
            ResultSet resultSet = connection.createStatement().executeQuery("select pagetitle,pagelink, (length(lower(pagetext))-length(replace(lower(pagetext),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "')  as countoccurences from pages order by countoccurences desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
//            for(SearchResult result:results){
//                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
//            }
            request.setAttribute("results",results);
            request.getRequestDispatcher("search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
