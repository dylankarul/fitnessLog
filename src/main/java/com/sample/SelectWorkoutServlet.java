/*
package com.sample;

import com.sample.model.WorkoutType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(
        name = "selectworkoutservlet",
        urlPatterns = "/SelectWorkout"
)
public class SelectWorkoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String workoutType = req.getParameter("Type");

        WorkoutService WorkoutService = new WorkoutService();
        WorkoutType l= WorkoutType.valueOf(workoutType);

        List WorkoutBrands = WorkoutService.getAvailableBrands(l);

        req.setAttribute("brands", WorkoutBrands);
        RequestDispatcher view = req.getRequestDispatcher("result.jsp");
        view.forward(req, resp);

    }
}*/
