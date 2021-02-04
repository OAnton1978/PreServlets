package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  public static final String ENDPOINT = "/api/posts";
  public static final String BORDER = "/";
  private PostController controller;

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      // primitive routing
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
  //  final var method = req.getMethod();

    if (path.equals(ENDPOINT)) {
      controller.all(resp);
      return;
    }
    if (path.matches(ENDPOINT + "\\d+")) {
      // easy way
      final var id = Long.parseLong(path.substring(path.lastIndexOf(BORDER)));
      controller.getById(id, resp);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
 //   final var method = req.getMethod();

    if (path.equals(ENDPOINT)) {
      controller.save(req.getReader(), resp);
      return;
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
 //   final var method = req.getMethod();

    if (path.matches(ENDPOINT + "\\d+")) {
      // easy way
      final var id = Long.parseLong(path.substring(path.lastIndexOf(BORDER)));
      controller.removeById(id, resp);
      return;
    }
  }
}

