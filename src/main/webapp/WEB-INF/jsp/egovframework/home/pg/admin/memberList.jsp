<%--
  Created by IntelliJ IDEA.
  User: joo
  Date: 2025. 11. 25.
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta name="description" content="" />
  <meta name="author" content="" />
  <title>OnRoom 관리자 회원관리 페이지</title>
  <!-- Favicon-->
  <link rel="icon" href="<c:url value='/assets/favicon.ico'/>"/>
  <!-- Core theme CSS (includes Bootstrap)-->
  <link href="<c:url value='/css/styles.css'/>" rel="stylesheet"/>

  <style>
    /* 기본 hover: primary 색상 */
    #sidebar-wrapper .list-group-item:hover {
      background-color: var(--bs-primary) !important;
      color: #fff !important;
    }

    /* active는 항상 primary 유지 */
    #sidebar-wrapper .list-group-item.active {
      background-color: var(--bs-primary) !important;
      color: #fff !important;
      border: none !important;
    }
  </style>

</head>
<body>
<div class="d-flex" id="wrapper">
  <!-- Sidebar-->
  <jsp:include page="/WEB-INF/jsp/egovframework/home/pg/admin/common/sidebar.jsp"></jsp:include>

  <!-- Page content wrapper-->
  <div id="page-content-wrapper">
    <!-- Top navigation-->
    <nav class="navbar navbar-expand-lg bg-light border-bottom">

    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
            <li class="nav-item mx-4"><a class="nav-link active" href="<c:url value='/admin/index.do'/>">홈</a></li>
            <li class="nav-item"><a class="nav-link active" href="<c:url value='/logout.do'/>">로그아웃</a></li>
          </ul>
        </div>
      </div>
    </nav>


    <!-- Page content-->
    <div class="container-fluid">
      <h1 class="mt-4">회원 관리</h1>

      <!-- 상단 툴바: 좌측 메타, 우측 검색 -->
      <div class="d-flex justify-content-between align-items-center mb-3">

        <%-- 리스트 수 표시--%>
        <span class="count text-muted small"></span>

        <!-- 우측: 검색 폼 -->
        <form id="searchForm" class="d-flex align-items-center gap-2">
          <input type="hidden" id="movePage" name="movePage" value="<c:out value='${param.movePage}' default='1' />">

          <select id="searchStatus" name="searchStatus" class="form-select w-auto">
            <option value="" <c:if test="${param.searchStatus eq ''}">selected</c:if>>회원 상태 선택</option>
            <c:forEach var="status" items="${statusList}">
              <option value="${status.code}" ${param.searchStatus == status.code ? 'selected' : ''}>${status.name}</option>
            </c:forEach>
          </select>

          <input id="searchQuery" name="searchQuery" type="text" class="form-control"  placeholder="회원 이름을 입력하세요" value="<c:out value="${param.searchQuery}" />">

          <button id="searchBtn" type="button" class="btn btn-dark flex-shrink-0">검색</button>

        </form>
      </div>

      <!-- 목록 -->
      <div class="table-responsive">
        <table  id="dataList" class="dataList table table-hover align-middle mb-0">
          <thead class="table-active">
          <tr>
            <th class="text-center">연번</th>
            <th class="text-center">권한</th>
            <th class="text-center">아이디</th>
            <th class="text-center">이름</th>
            <th class="text-center">휴대전화</th>
            <th class="text-center">이메일</th>
            <th class="text-center">생년월일</th>
            <th class="text-center">등록일</th>
            <th class="text-center">수정일</th>
            <th class="text-center">삭제일</th>
            <th class="text-center">최근 로그인</th>
            <th class="text-center">상태</th>
            <th class="text-center">관리</th>
          </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>

      <!-- 페이지네이션 -->
      <div id="memberPage" class="d-flex justify-content-center mt-5">
        <div class="pagination"></div>
      </div>

      <!-- 회원 관리 모달 -->
      <div id="memberModal" class="modal fade" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">

            <div class="modal-header bg-dark text-white">
              <h5 id="modalTitle" class="modal-title">회원 관리</h5>
              <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
              <form id="modalForm" enctype="multipart/form-data">
                <!-- memberId -->
                <input id="memberId" name="memberId" type="hidden" value="">

                <p class="small"><span class="text-danger mb-3">*</span> 는 필수 입력 사항입니다.</p>

                <div class="mb-3">
                  <label for="username" class="form-label fw-bold">아이디 <span class="text-danger mb-3">*</span></label>
                  <input id="username" name="username" type="text" class="form-control" placeholder="아이디를 입력하세요" required>
                </div>

                <div class="mb-3">
                  <label for="name" class="form-label fw-bold">이름 <span class="text-danger mb-3">*</span></label>
                  <input id="name" name="name" type="text" class="form-control" placeholder="이름을 입력하세요" required>
                </div>

                <div class="mb-3">
                  <label for="phone" class="form-label fw-bold">휴대전화 <span class="text-danger mb-3">*</span></label>
                  <input id="phone" name="phone" class="form-control" placeholder="숫자만 입력하세요. ex) 01012345678" required></input>
                </div>

                <div class="mb-3">
                  <label for="email" class="form-label fw-bold">이메일 <span class="text-danger mb-3">*</span></label>
                  <input id="email" name="email" type="email" class="form-control" placeholder="이메일을 입력하세요." required>
                </div>

                <div class="mb-3">
                  <label for="birthdate" class="form-label fw-bold">생년월일 <span class="text-danger mb-3">*</span></label>
                  <input id="birthdate" name="birthdate" type="date" class="form-control" required>
                </div>

                <div class="mb-3">
                  <label for="status" class="form-label fw-bold">상태 <span class="text-danger mb-3">*</span></label>
                  <select id="status" name="status" class="form-select" required>
                  </select>
                </div>

                <div class="mb-3">
                  <label for="role" class="form-label fw-bold">권한 <span class="text-danger mb-3">*</span></label>
                  <div id="role"></div>
                </div>

              </form>
            </div>

            <div class="modal-footer d-flex justify-content-center">
              <button id="memberSaveBtn" type="button" class="btn btn-primary">저장</button>
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>

          </div>
        </div>
      </div>


    </div>
  </div>

</div>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="<c:url value='/js/scripts.js'/>"></script>

<script>
//페이지 로드가 완료되면
if (window.addEventListener) window.addEventListener('load', memberList, false);
else if (window.attachEvent) window.attachEvent('onload', memberList);
else window.onload = memberList;

function memberList() {
  dataList();

  event();
}

function dataList() {
  // 이전 목록 제거
  // $('.dataList tbody').children('tr').remove('');
  $('.dataList tbody').empty();
  // 로딩 바
  const constColLen = $('.dataList thead tr').children('th').length;

  $('.dataList tbody').append('<tr class="loading"><td colspan="' + constColLen + '" class="text-center"><i class="fad fa-spinner-third fa-spin fa-5x"></i></td></tr>');

  // form Submit [url, form, swal or toastr, validate, funcData]
  ajaxForm('<c:url value="/admin/getMemberList.do"/>', $('#searchForm').serialize(), function(data) {
    if ($.trim(data.error) == 'N') {
      let tableData = '';
      let trClass = '';
      let page = Number(data.dataMap.page);
      let pageCnt = Number(data.dataMap.pageCnt);
      let recordCnt = Number(data.dataMap.recordCnt ?? data.dataMap.list?.length ?? 0);
      let totalCnt = Number(data.dataMap.totalCnt);

      if (!recordCnt) recordCnt = 0;
      let startNo = totalCnt - ((page - 1) * recordCnt);

      // 회원 상태 맵
      let statusMap = {};
      (data.dataMap.statusList || []).forEach(function(rt){
        statusMap[rt.code] = rt.name;
      });

      // 회원 권한 맵
      let roleMap = {};
      (data.dataMap.roleList || []).forEach(function(r) {
        roleMap[r.roleId] = r.description;
      });

      $.each(data.dataMap.list, function(key, values) {
        tableData += '<tr' + trClass + '>';
        // 연번
        tableData += '<td class="text-center">' + (startNo - key) + '</td>';
        // 권한
        // let roleArr = '';
        // (values.role || []).forEach(function(r) {
        //   roleArr += roleMap[r];
        // })
        tableData += '<td class="text-center">' + values.role + '</td>';
        // 아이디
        tableData += '<td class="text-center">' + (values.role === '소셜 사용자' ? '소셜 사용자' : values.username) + '</td>';
        // 이름
        tableData += '<td class="text-center">' + $.trim(values.name) + '</td>';
        // 휴대전화
        tableData += '<td class="text-center">' + values.phone + '</td>';
        // 이메일
        tableData += '<td class="text-center">' + values.phone + '</td>';
        // 생년월일
        tableData += '<td class="text-center">' + formatDate(values.birthdate) + '</td>';
        // 등록일
        tableData += '<td class="text-center">' + formDateTime(values.regDt) + '</td>';
        // 수정일
        tableData += '<td class="text-center">' + (values.updtDt ? formDateTime(values.updtDt) : '-') + '</td>';
        // 삭제일
        tableData += '<td class="text-center">' + (values.delDt ? formDateTime(values.delDt) : '-') + '</td>';
        // 최근 로그인
        tableData += '<td class="text-center">' + (values.lastLogined ? formDateTime(values.lastLogined) : '-') + '</td>';
        // 상태
        tableData += '<td class="text-center">' + statusMap[values.status] + '</td>';

        // 관리
        tableData += '<td class="text-center">' +
                '<button type="button" class="managementBtn btn btn-success" ' +
                'data-member-id="' + values.memberId + '">관리</button>' +
                '</td>';

        tableData += '</tr>';
      });

      // data 0 이면 "데이터가 없습니다"
      if (totalCnt === 0) {
        tableData += '<tr><td colspan="' + constColLen + '" class="no-data text-center text-secondary">데이터가 없습니다.</td></tr>';
        // 현재 페이지 / 전체 페이지
        $('.count').html('총 <strong>' + numberWithCommas(totalCnt) + '건</strong> | 페이지 <strong>' + '0' + '/' + pageCnt + '</strong>');
      } else {
        $('.count').html('총 <strong>' + numberWithCommas(totalCnt) + '건</strong> | 페이지 <strong>' + page + '/' + pageCnt + '</strong>');
      }
      $('.dataList tbody').append(tableData);
      // 페이징
      customPagination('memberPage', data.dataMap.page, data.dataMap.pageCnt);
    }
    // 로딩 제거
    $('.dataList tbody').children('tr.loading').remove('');
  });
}

function event() {
  // 검색 버튼 클릭
  $('#searchBtn').on('click', function () {
    $('#searchForm').find('input[name="movePage"]').val('1');
    dataList();
  });

  // 페이징 버튼 클릭
  $('.pagination').on('click', 'li a', function (e) {
    // # 붙는 기본동작 막기
    e.preventDefault();
    // #searchFormd 안의 movePage 값을 클릭된 페이지 번호로 바꿈
    $('#searchForm').find('input[name="movePage"]').val($(this).data('move'));
    dataList();
  });

  // 검색 입력: 한글/영어/숫자만 가능
  $('#searchQuery').on('input', function () {
    const filtered = $(this).val().replace(/[^가-힣ㄱ-ㅎㅏ-ㅣ0-9a-zA-Z\s]/g, '');
    $(this).val(filtered);
  });

  // 관리 버튼 클릭
  $('.dataList').on('click', '.managementBtn', function () {
    const memberId = $(this).data('memberId'); // .attr은 문자열로 읽는데, .data는 데이터 타입이 js내에서 유지됨
    memberModal(memberId);
  });

  // 회원 저장 버튼 클릭
  // 모달 내 회원 관리 폼에서 저장 버튼 클릭
  $('#managementBtn').on('click', function () {
    // === 제출 유효성 체크 ===
    let formErr = false;
    let moveFocus = '';
    let errorMsg = '';

    if (formErr) {
      alert(errorMsg);
      if (moveFocus) {
        $('#' + moveFocus).focus();
      }
      return;
    }

    // merge
    ajaxForm('<c:url value="/admin/setMemberMerge.do"/>', $('#modalForm').serialize(), function (res) {
      if ($.trim(res.error) === 'N') {
        alert(res.successMsg);

        $('#memberModal').modal('hide');
        dataList();
      }
    });


  });

}

// 날짜 형식 변환
function formatDate(dataString) {
  const dateObj = new Date(dataString);
  const year = dateObj.getFullYear();
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0');
  const day = dateObj.getDate().toString().padStart(2, '0');

  return year + '-' + month + '-' + day;
}

function numberWithCommas(a) {
  return a.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
}

// 회원 관리 모달
// (memberId)
function memberModal(memberId) {
  // 폼/히든값 초기화
  $('#modalForm')[0].reset();
  $('#memberId').val('');

  ajaxForm('<c:url value="/admin/getMember.do"/>', {memberId: memberId}, function (res) {
    if (res.error === 'N') {
      const statusList = res.statusList;
      const roleList = res.roleList;
      const data = res.dataMap;

      $('#memberId').val(data.memberId);
      $('#username').val(data.username);
      $('#name').val(data.name);
      $('#phone').val(data.phone);
      $('#email').val(data.email);
      $('#birthdate').val(formatDate(data.birthdate));

      // 회원 상태
      let statusHtml = '';
      statusList.forEach(function (s) {
        const selected = (s.code === data.status) ? ' selected' : '';
        statusHtml += '<option value="' + s.code + '"' + selected + '>' + s.name + '</option>';
      });

      $('#status').html(statusHtml);

      // 회원 권한
      let roleHtml = '';
      roleList.forEach(function (r) {
        const checked = (data.role.indexOf(r.roleId) !== -1) ? ' checked' : '';
        roleHtml += '<div class="form-check form-check-inline">';
        roleHtml += '<input class="form-check-input"' +
                'type="checkbox" ' +
                'id="' + r.name + '" ' +
                'name="roleIds" ' +
                'value="' + r.roleId + '"' + checked + '>';
        roleHtml += '<label class="form-check-label" for="' + r.name + '">' +
                r.description +
                '</label>';
        roleHtml += '</div>';
      });

      $('#role').html(roleHtml);

      // 회원 role에 3(소셜 사용자)가 있으면 이름을 '소셜 사용자'로 세팅
      if (data.role.indexOf(3) !== -1) {
        $('#username').val('소셜 사용자');
      }

      let modalEl = document.getElementById('memberModal');
      let modal = new bootstrap.Modal(modalEl);
      modal.show();
    }
  });

}

</script>

</body>
</html>
