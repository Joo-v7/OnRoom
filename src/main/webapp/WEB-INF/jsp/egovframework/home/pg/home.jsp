<%--
  Created by IntelliJ IDEA.
  User: joo
  Date: 2025. 11. 8.
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/header.jsp"/>

<!-- Page content-->
<div class="container-xl">
  <div class="row py-5">

    <!-- sidebar -->
    <div class="col-lg-2">
      <jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/sidebar.jsp"/>
    </div>

    <!-- Blog entries-->
    <div class="col-lg-10">
      <!-- Featured blog post-->
      <div class="card mb-4">
        <a href="#!"><img class="card-img-top" src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg" alt="..." /></a>
        <div class="card-body">
          <div class="small text-muted">January 1, 2023</div>
          <h2 class="card-title">Featured Post Title</h2>
          <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus possimus, veniam magni quis!</p>
          <a class="btn btn-primary" href="#!">Read more →</a>
        </div>
      </div>
      <!-- Nested row for non-featured blog posts-->
      <div class="row">
        <div class="col-lg-6">
          <!-- Blog post-->
          <div class="card mb-4">
            <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="..." /></a>
            <div class="card-body">
              <div class="small text-muted">January 1, 2023</div>
              <h2 class="card-title h4">Post Title</h2>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla.</p>
              <a class="btn btn-primary" href="#!">Read more →</a>
            </div>
          </div>
          <!-- Blog post-->
          <div class="card mb-4">
            <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="..." /></a>
            <div class="card-body">
              <div class="small text-muted">January 1, 2023</div>
              <h2 class="card-title h4">Post Title</h2>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla.</p>
              <a class="btn btn-primary" href="#!">Read more →</a>
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <!-- Blog post-->
          <div class="card mb-4">
            <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="..." /></a>
            <div class="card-body">
              <div class="small text-muted">January 1, 2023</div>
              <h2 class="card-title h4">Post Title</h2>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla.</p>
              <a class="btn btn-primary" href="#!">Read more →</a>
            </div>
          </div>
          <!-- Blog post-->
          <div class="card mb-4">
            <a href="#!"><img class="card-img-top" src="https://dummyimage.com/700x350/dee2e6/6c757d.jpg" alt="..." /></a>
            <div class="card-body">
              <div class="small text-muted">January 1, 2023</div>
              <h2 class="card-title h4">Post Title</h2>
              <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex quis soluta, a laboriosam.</p>
              <a class="btn btn-primary" href="#!">Read more →</a>
            </div>
          </div>
        </div>
      </div>
      <!-- Pagination-->
      <nav aria-label="Pagination">
        <hr class="my-0" />
        <ul class="pagination justify-content-center my-4">
          <li class="page-item disabled"><a class="page-link" href="#" tabindex="-1" aria-disabled="true">Newer</a></li>
          <li class="page-item active" aria-current="page"><a class="page-link" href="#!">1</a></li>
          <li class="page-item"><a class="page-link" href="#!">2</a></li>
          <li class="page-item"><a class="page-link" href="#!">3</a></li>
          <li class="page-item disabled"><a class="page-link" href="#!">...</a></li>
          <li class="page-item"><a class="page-link" href="#!">15</a></li>
          <li class="page-item"><a class="page-link" href="#!">Older</a></li>
        </ul>
      </nav>
    </div>

</div>

<jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/footer.jsp"/>