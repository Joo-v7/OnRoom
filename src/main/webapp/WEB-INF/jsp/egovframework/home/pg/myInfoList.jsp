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
          <h2 class="display-6 fw-bold mb-3">내 정보</h2>

          <table class="dataList table table-group-divider">
            <tbody>
            </tbody>
          </table>

          <!-- 회원 정보 수정 모달 -->
          <div id="infoUpdateModal" class="modal fade" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
              <div class="modal-content">

                <div class="modal-header bg-dark text-white">
                  <h5 id="modalTitle" class="modal-title">회원 정보 수정</h5>
                  <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">
                  <form id="modalForm" enctype="multipart/form-data">

                    <!-- username -->
                    <input id="updateUsername" name="username" type="hidden" value="">

                    <!-- target -->
                    <div id="updateTarget" class="mb-3">
                    </div>

                    <div class="modal-footer d-flex justify-content-center">
                      <button id="infoSaveBtn" type="button" class="btn btn-primary">저장</button>
                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                    </div>


                  </form>
                </div>

              </div>
            </div>
          </div>


        </div>
      </div>
    </div>

  </div>
</div>
<script>
//페이지 로드가 완료되면
if (window.addEventListener) window.addEventListener('load', myInfo, false);
else if (window.attachEvent) window.attachEvent('onload', myInfo);
else window.onload = myInfo;

// 이벤트 정의
function myInfo() {

  dataList();

  event();

}

function dataList() {
  $('.dataList tbody').empty();

  ajaxForm('<c:url value="/myPage/getMyInfoList.do"/>', null, function(data) {
    if ($.trim(data.error) === 'N') {
      let tableData = '';
      const dataMap = data.dataMap;

      // 수정 모달 폼에 username 세팅
      $('#updateUsername').val(dataMap.username);

      let username = (dataMap.isOauth === 'Y') ? (dataMap.oauthProvider + ' 로그인 사용자') : dataMap.username;

      // 아이디
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '아이디' + '</th>';
      tableData += '<td>' + $.trim(username) + '</td>';
      tableData += '</tr>';

      // 이름
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '이름' + '</th>';
      tableData += '<td>';
      tableData += '<span>' + $.trim(dataMap.name) + '</span>';
      tableData += '<button type="button" class="editBtn btn btn-outline-secondary btn-sm float-end" ' +
              'data-name="name" ' +
              'data-label="이름" ' +
              'data-type="text" ' +
              'data-value="' + $.trim(dataMap.name) + '">수정하기</button>';
      tableData += '</td>';
      tableData += '</tr>';

      // 전화번호
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '전화번호' + '</th>';
      tableData += '<td>';
      tableData += '<span>' + dataMap.phone + '</span>';
      tableData += '<button type="button" class="editBtn btn btn-outline-secondary btn-sm float-end" ' +
              'data-name="phone" ' +
              'data-label="전화번호" ' +
              'data-type="text" ' +
              'data-value="' + dataMap.phone + '">수정하기</button>';
      tableData += '</td>';
      tableData += '</tr>';

      // 이메일
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '이메일' + '</th>';
      tableData += '<td>';
      tableData += '<span>' + dataMap.email + '</span>';
      tableData += '<button type="button" class="editBtn btn btn-outline-secondary btn-sm float-end" ' +
              'data-name="email" ' +
              'data-label="이메일" ' +
              'data-type="email" ' +
              'data-value="' + dataMap.email + '">수정하기</button>';
      tableData += '</td>';
      tableData += '</tr>';

      // 생년월일
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '생년월일' + '</th>';
      tableData += '<td>';
      tableData += '<span>' + formatDate(dataMap.birthdate) + '</span>';
      tableData += '<button type="button" class="editBtn btn btn-outline-secondary btn-sm float-end" ' +
              'data-name="birthdate" ' +
              'data-label="생년월일" ' +
              'data-type="date" ' +
              'data-value="' + formatDate(dataMap.birthdate) + '">수정하기</button>';
      tableData += '</td>';
      tableData += '</tr>';

      // 최근 로그인 일시
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '최근 로그인' + '</th>';
      tableData += '<td>' + formDateTime(dataMap.lastLogined) + '</td>';
      tableData += '</tr>';

      // 가입일시
      tableData += '<tr>';
      tableData += '<th class="bg-light text-center">' + '가입일' + '</th>';
      tableData += '<td>' + formDateTime(dataMap.regDt) + '</td>';
      tableData += '</tr>';

      $('.dataList tbody').append(tableData);

    }

  });
}

// 이벤트 정의
function event() {

  // 수정 버튼
  $('.dataList').on('click', '.editBtn', function() {
    const name = $(this).data('name'); // 서버에 보낼 name (name, phone, emial, birthdate)
    const label = $(this).data('label'); // 모달에 표시할 한글 라벨
    const type = $(this).data('type');
    const value = $(this).data('value');

    // 모달 타이틀
    // $('#modalTitle').text(label + ' 수정');

    let placeholder = '';
    let maxlength = '';

    // placeholder/maxlength 세팅
    if (name === 'name') {
      placeholder = '한글, 영어만 입력 가능합니다.';
      maxlength = 20;
    } else if (name === 'phone') {
      placeholder = '숫자만 입력하세요. ex) 01012345678';
      maxlength = 13;
    } else if (name === 'email') {
      placeholder = '이메일을 입력하세요.';
      maxlength = 100;
    }

    // 모달 내용 (id=updateTarget 넣을 html)
    let html = '';
    html += '<label for="updateField" class="form-label fw-bold">' + label + '</label>';

    // 생년월일이면
    if (name === 'birthdate') {
      html += '<input id="updateField" name="' + name + '" type="date" class="form-control" ' + 'value="' + value + '">';
    } else {
      html += '<input id="updateField" name="' + name + '" type="' + type + '" class="form-control" ' +
              (placeholder ? 'placeholder="' + placeholder + '" ' : '') +
              (maxlength ? 'maxlength="' + maxlength + '" ' : '') +
              'value="' + value + '">';
    }

    $('#updateTarget').html(html);

    // 모달 열기
    let modalEl = document.getElementById('infoUpdateModal');
    let modal = new bootstrap.Modal(modalEl);
    modal.show();

  });

  // 모달 저장 버튼 클릭
  $('#infoSaveBtn').on('click', function () {
    saveUpdatedInfo();
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

// 날짜 + 시간(분:초)
function formDateTime(dataString) {
  const dateObj = new Date(dataString);

  const year = dateObj.getFullYear();
  const month = (dateObj.getMonth() + 1).toString().padStart(2, '0');
  const day = dateObj.getDate().toString().padStart(2, '0');
  const hours = String(dateObj.getHours()).padStart(2, "0");
  const minutes = String(dateObj.getMinutes()).padStart(2, "0");

  return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes;
}

// 회원 정보 수정 모달창 - 저장
function saveUpdatedInfo() {
  const value = $('#updateTarget').find('#updateField').val();
  console.log(value);

  // ==== 제출 유효성 검사 ====
  let formErr = false;
  let errMsg = '';



  if (formErr) {
    alert(errMsg);
    return;
  }

  ajaxForm('<c:url value="/myPage/setMyInfoUpdate.do"/>', $('#modalForm').serialize(), function (res) {
    if ($.trim(res.error) === 'N') {
      alert(res.successMsg);
      // 모달 닫기
      $('#infoUpdateModal').modal('hide');
      // 목록 다시 조회
      dataList();
    }
  });


}

// 단일 필드 저장
function saveInfoField() {
  const $input = $('#updateTarget').find('#updateField');
  const fieldName = $input.attr('name');         // name, phone, email, birthdate
  const fieldValue = $.trim($input.val());
  const username = $('#updateUsername').val();

  let formErr = false;
  let errMsg = '';

  // 공통 필수 검사
  if (!fieldValue) {
    formErr = true;
    errMsg = '값을 입력해 주세요.';
  }

  // 필드별 검증
  if (!formErr && fieldName === 'email') {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(fieldValue)) {
      formErr = true;
      errMsg = '올바른 이메일 형식을 입력해 주세요.';
    }
  }

  if (!formErr && fieldName === 'phone') {
    const phoneRegex = /^[0-9\-]{9,20}$/;
    if (!phoneRegex.test(fieldValue)) {
      formErr = true;
      errMsg = '전화번호는 숫자와 - 만 입력 가능합니다.';
    }
  }

  if (!formErr && fieldName === 'name') {
    const nameRegex = /^[가-힣a-zA-Z\s]{1,50}$/;
    if (!nameRegex.test(fieldValue)) {
      formErr = true;
      errMsg = '이름은 50자 이하의 한글 또는 영문만 입력 가능합니다.';
    }
  }

  if (formErr) {
    alert(errMsg);
    $input.focus();
    return;
  }

  // ajax 전송 (username + 수정할 필드 한 개)
  ajaxForm('<c:url value="/myPage/setMyInfoUpdate.do"/>', $('#modalForm').serialize(), function(res) {
    if ($.trim(res.error) === 'N') {
      alert(res.successMsg || '정상적으로 수정되었습니다.');

      // 모달 닫기
      $('#infoUpdateModal').modal('hide');

      // 목록 다시 조회
      dataList();
    }
  });


}


</script>

<%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/footer.jsp"%>