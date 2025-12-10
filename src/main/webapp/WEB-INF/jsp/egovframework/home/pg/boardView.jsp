<%--
  Created by IntelliJ IDEA.
  User: joo
  Date: 2025. 11. 11.
  Time: 00:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/header.jsp" %>


<form id="searchForm" name="searchForm" method="post">
  <input type="hidden" id="movePage" name="movePage" value="<c:out value="${param.movePage}" />">
  <input type="hidden" id="searchBoardType" name="searchBoardType" value="<c:out value="${param.searchBoardType}" />">
  <input type="hidden" id="searchQuery" name="searchQuery" value="<c:out value="${param.searchQuery}" />">
</form>

<!-- Page content-->
<div class="container-xl">
  <div class="row py-5">

    <!-- sidebar -->
<%--    <div class="col-lg-2">--%>
<%--      <%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/sidebar.jsp" %>--%>
<%--    </div>--%>

    <!-- Content entries-->
    <div class="col-lg-12">
      <div class="row justify-content-center mb-auto">
        <div class="col-12 col-md-7">

          <!-- 제목 -->
          <h3 class="fw-semibold mb-3"><c:out value="${board.title}"/></h3>
          <!-- 메타 라인 -->
          <div class="border-top border-bottom pt-3 pb-3 small text-muted d-flex gap-3">
            <div><strong class="text-dark me-3">분류</strong> <span class="text-secondary"><c:out value="${board.boardTypeName}"/></span></div>
            <div class="vr"></div>
            <div><strong class="text-dark me-3">작성자</strong> <span class="text-secondary"><c:out value="${board.name}"/></span></div>
            <div class="vr"></div>
            <div><strong class="text-dark me-3">작성일</strong> <span><fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd HH:mm"/></span></div>
            <div class="vr"></div>
            <div><strong class="text-dark me-3">조회수</strong> <span><c:out value="${board.viewCount}"/></span></div>

            <c:if test="${not empty currentMemberId and currentMemberId eq board.memberId}">
              <div class="ms-auto">
                <a href="javascript:void(0)" id="boardUpdate" class="text-decoration-none text-primary me-2">수정</a>
                <a href="javascript:void(0)" id="boardDelete" class="text-decoration-none text-danger">삭제</a>
              </div>
            </c:if>

          </div>

          <!-- 본문 -->
          <div class="border-bottom pb-4 pt-3" style="min-height: 30vh;">
            <div class="content">
              <c:out value="${board.content}" />
            </div>
          </div>

          <!-- 댓글 개수, 등록 -->
          <div class="pb-4 pt-3">
            <div id="count"></div>

            <!-- 댓글 입력 -->
            <form id="commentForm" class="mt-3 d-flex gap-2">
              <input type="hidden" id="boardId" name="boardId" value="<c:out value='${param.boardId}'/>">
              <input type="hidden" id="parentId" name="parentId" value="">
              <input type="hidden" id="depth" name="depth" value="">
              <input type="hidden" id="priority" name="priority" value="">

              <input type="text" id="content" name="content" class="form-control" placeholder="댓글을 남겨보세요." required maxlength="200"/>
              <button type="submit" class="btn btn-dark" style="white-space: nowrap;">저장</button>
            </form>

          </div>

          <!-- 댓글 내용 -->
          <div>
            <ul id="comment" class="list-group list-group-flush">
            </ul>
          </div>

          <!-- 목록 버튼 -->
          <div class="text-center my-4">
            <button id="boardList" type="button" class="btn btn-secondary px-5">목록</button>
          </div>

        </div>
      </div>

    </div>
  </div>
</div>
<script>
  //페이지 로드가 완료되면
  if (window.addEventListener) window.addEventListener('load', view, false);
  else if (window.attachEvent) window.attachEvent('onload', view);
  else window.onload = view;

  // 이벤트 정의
  function view() {

    // 댓글
    commentList();

    // 목록 이동 버튼
    $('#boardList').on('click', function(){
      boardList();
    });

    // 게시글 수정 버튼
    $('#boardUpdate').on('click', function() {
      let boardId = $('#boardId').val();

      window.location.href = '<c:url value="/board.do?action=update"/>' + '&boardId=' + boardId;

    });

    // 게시글 삭제 버튼
    $('#boardDelete').on('click', function() {
      if (confirm("게시글을 삭제하시겠습니까?")) {

        let boardId = $('#boardId').val();

        ajaxForm('<c:url value="/setBoardDelete.do"/>', {boardId: boardId}, function(res) {
          if ($.trim(res.error) === 'N') {
            boardList();
          }
        });

      }

    });

    // 댓글 등록 버튼
    $('#commentForm').on('submit', function(e) {
      e.preventDefault();
      submitCommentForm();
    });

    // 댓글 삭제 클릭
    $('#comment').on('click', '.deleteComment', function (e) {
      e.preventDefault();

      if (confirm("댓글을 삭제하시겠습니까?")) {

        let $this = $(this);
        let commentId = $this.data('commentId');
        let commentMemberId = $this.data('memberId');

        let map = {
          commentId: commentId,
          commentMemberId: commentMemberId
        };

        ajaxForm('<c:url value="/setCommentDelete.do"/>', map, function (res) {
          if ($.trim(res.error) === 'N') {
            commentList();
          }
        });
      }
    });

    // 댓글 수정 클릭
    $('#comment').on('click', '.updateComment', function (e) {
      e.preventDefault();

      let $this = $(this);
      let commentId = $this.data('commentId');
      let $li = $this.closest('li');
      let $contentDiv = $li.find('.comment-content');

      // 이미 수정 모드면 또 안 열기
      if ($li.data('editing') === true) {
        return;
      }
      $li.data('editing', true);

      let originalText = $.trim($contentDiv.text());

      // 원본 내용은 data 로 보관 (혹시 쓸 일 있을까봐)
      $contentDiv.data('original-content', originalText);

      // 수정 입력창 DOM 만들기
      let editHtml = '';
      editHtml += '<div class="mt-1 comment-edit input-group">';
      editHtml += '  <input type="text" class="form-control editContent" value="' + escapeHtml(originalText) + '" maxlength="200"/>';
      editHtml += '  <button type="button" class="btn btn-dark btn-sm saveComment" data-comment-id="' + commentId + '">저장</button>';
      editHtml += '  <button type="button" class="btn btn-outline-secondary btn-sm cancelComment">취소</button>';
      editHtml += '</div>';

      // 기존 내용 숨기고, 바로 뒤에 수정 영역 추가
      $contentDiv.hide();
      $contentDiv.after(editHtml);
    });

    // 댓글 수정 취소 클릭
    $('#comment').on('click', '.cancelComment', function (e) {
      e.preventDefault();

      let $li = $(this).closest('li');
      let $contentDiv = $li.find('.comment-content');

      // 수정 영역 제거, 원본 다시 표시
      $li.find('.comment-edit').remove();
      $contentDiv.show();
      $li.data('editing', false);
    });

    // 댓글 수정 저장 클릭
    $('#comment').on('click', '.saveComment', function (e) {
      e.preventDefault();

      let $this = $(this);
      let commentId = $this.data('commentId');
      let $li = $this.closest('li');
      let newContent = $.trim($li.find('.editContent').val());
      let boardId = $('#boardId').val();

      if (newContent === '') {
        alert('내용을 입력해주세요.');
        return;
      }

      // 보낼 데이터 (필요하면 boardId 도 같이 보낼 수 있음)
      let map = {
        commentId: commentId,
        content: newContent,
        boardId: boardId
      };

      ajaxForm('<c:url value="/setCommentMerge.do"/>', map, function (res) {
        if ($.trim(res.error) === 'N') {
          // 목록 다시 조회
          commentList();
        }
      });
    });

  }

  // 댓글
  function commentList() {
    $('#comment').empty();
    $('#count').empty();

    let boardId = $('#boardId').val();

    ajaxForm('<c:url value="/getCommentList.do"/>', {boardId: boardId}, function(data) {
      if ($.trim(data.error) === 'N') {
        let totalCnt = Number(data.dataMap.totalCnt);
        let currentMemberId = data.dataMap.currentMemberId;
        let html = '';

        $.each(data.dataMap.list, function(idx, item) {
          if ($.trim(item.visibleYn) === 'N') {
            // 삭제 처리된 댓글
            html += '<li class="list-group-item py-3">' +
                    '<div class="text-muted small">' + '삭제된 댓글입니다.' + '</div>' +
                    '</li>';
          } else { // 댓글
            html += '<li class="list-group-item py-3">' +
                    '<div class="d-flex justify-content-between align-content-center">' +
                    '<div class="fw-bold">' + item.name + '</div>' +
                    '<div class="text-muted small">' + formDateTime(item.regDt) + '</div>' +
                    '</div>' +
                    '<div class="mt-1 comment-content">' + item.content + '</div>' +
                    '<div class="d-flex justify-content-between align-content-center mt-3">';
            // 로그인 한 유저면
            if (currentMemberId) {
              // 왼쪽 답글 영역
              html += '<div>';
              // html += '<button type="button" class="btn btn-sm btn-outline-secondary" data-comment-id="' + item.commentId + '">답글</button>';
              html += '</div>';
              // 오른쪽 수정/삭제 영역
              html += '<div>';
              if (currentMemberId === item.memberId) {
                html += '<a href="javascript:void(0)" class="updateComment text-decoration-none text-secondary me-2 " data-comment-id="' + item.commentId + '">수정</a>';
                html += '<a href="javascript:void(0)" class="deleteComment text-decoration-none text-secondary" ' +
                        'data-comment-id="' + item.commentId + '" ' +
                        'data-member-id="' + item.memberId + '">삭제</a>';
              }
              html += '</div>';
            }
            html += '</div>' +
                    '</li>';
          }

        });

        // data 0 이면 "댓글이 없습니다"
        if (totalCnt === 0) {
          $('#count').html('댓글 <strong>' + totalCnt + '</strong>');
        } else {
          $('#count').html('댓글 <strong>' + totalCnt + '</strong>');
        }

        $('#comment').append(html);
      }

    });
  }

  // 게시판 목록으로 이동
  function boardList() {
    let $form = $('#searchForm');
    $form.attr('action', "/boardList.do");
    $form.submit();
  }

  // 댓글 등록
  function submitCommentForm() {

    ajaxForm('<c:url value="/setCommentMerge.do"/>', $('#commentForm').serialize(), function (res) {
      if ($.trim(res.error) === 'N') {
        // 댓글 목록 다시 조회
        commentList();
      }
    });

    // 댓글 입력부분 비우기
    $('#content').val('');

  }

  // HTML escape (input value 세팅용)
  function escapeHtml(str) {
    if (!str) return '';
    return String(str)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
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

</script>


<%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/footer.jsp"%>