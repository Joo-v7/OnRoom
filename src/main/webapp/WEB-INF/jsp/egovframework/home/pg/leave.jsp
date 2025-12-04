<%--
  Created by IntelliJ IDEA.
  User: joo
  Date: 2025. 11. 11.
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/header.jsp" %>

<!-- Page content-->
<div class="container-xl">
  <div class="row py-5">

    <!-- sidebar -->
    <div class="col-lg-2 mt-5">
      <jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/sidebar.jsp">
        <jsp:param name="sideType" value="myPage"/>
        <jsp:param name="active" value="account"/>
      </jsp:include>
    </div>

    <!-- Content entries-->
    <div class="col-lg-8">
      <div class="row justify-content-center mb-auto">
        <div class="col-12 col-md-10 col-lg-10 mx-auto">
          <h2 class="display-6 fw-bold mb-3">회원 탈퇴</h2>

          <!-- 제목 아래에 선 -->
          <hr class="border-5 opacity-100 mb-4 mt-0"/>

            <form id="leaveForm" class="g-3">

              <div class="col-md-6 mb-3">
                <label for="checkPassword" class="form-label fw-bold">비밀번호 확인</label>
                <input id="checkPassword" name="checkPassword" class="checkPassword form-control" type="password">
              </div>

              <button type="submit" class="leaveBtn btn btn-outline-dark fw-bold mt-2 w-50">탈퇴하기</button>

            </form>


        </div>
      </div>
    </div>

  </div>
</div>
<script>
//페이지 로드가 완료되면
if (window.addEventListener) window.addEventListener('load', event, false);
else if (window.attachEvent) window.attachEvent('onload', event);
else window.onload = event;

// 이벤트
function event() {

  // 탈퇴 버튼 클릭
  $('.leaveBtn').on('click', function (e) {
    e.preventDefault();

    let $form = $('#leaveForm');

    ajaxForm('<c:url value="/myPage/setLeave.do"/>', $form.serialize(), function (res) {
      if ($.trim(res.error === 'N')) {
        alert(res.successMsg);
        window.location.replace("/logout.do");
      }
    });


  });


}

</script>

<%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/footer.jsp"%>