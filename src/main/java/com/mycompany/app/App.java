package com.mycompany.app;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App
{

    public static boolean search(ArrayList<Integer> array, int number1,
			int number2) {

		System.out.println("Searching..");

		if (array == null) {
			return false;
		}

		if (number1 < 0) {
			return false;
		}
		if (number2 < 0) {
			return false;
		}

		int sumOfNumbers = number1 + number2;
		int smallestOfList = 0;
		int biggestOfList = 0;
		for (int i = 0; i < array.size(); i++) {

			if (i == 0) {
				smallestOfList = array.get(i);
			} else {
				if (array.get(i) < smallestOfList) {
					smallestOfList = array.get(i);
				}
			}

		}

		for (int j = 0; j < array.size(); j++) {

			if (j == 0) {
				biggestOfList = array.get(j);
			} else {
				if (array.get(j) > biggestOfList) {
					biggestOfList = array.get(j);
				}
			}

		}
	if ((sumOfNumbers <= biggestOfList) && (sumOfNumbers >= smallestOfList)) {

			return true;
		} else {
			return false;
		}
	}


    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {
          //System.out.println(req.queryParams("input1"));
          //System.out.println(req.queryParams("input2"));

          String input1 = req.queryParams("input1");
          java.util.Scanner sc1 = new java.util.Scanner(input1);
          sc1.useDelimiter("[;\r\n]+");
          java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
          while (sc1.hasNext())
          {
            int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
            inputList.add(value);
          }
          System.out.println(inputList);


          String input2 = req.queryParams("input2").replaceAll("\\s","");
          int input2AsInt = Integer.parseInt(input2);
	  String input3 = req.queryParams("input3").replaceAll("\\s","");
	  int input3AsInt = Integer.parseInt(input2);


          boolean result = App.search(inputList, input2AsInt , input3AsInt);

          Map map = new HashMap();
          map.put("result", result);
          return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
            (rq, rs) -> {
              Map map = new HashMap();
              map.put("result", "not computed yet!");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
