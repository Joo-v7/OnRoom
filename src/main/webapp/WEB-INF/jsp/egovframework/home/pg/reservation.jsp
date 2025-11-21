<%--
  Created by IntelliJ IDEA.
  User: joo
  Date: 2025. 11. 20.
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/header.jsp"/>

<style>
  /* 날짜 숫자 기본 색 */
  .fc-daygrid-day-number {
    color: #333 !important;
  }

  /* FullCalendar 요일 헤더의 링크(a)에서 Bootstrap 링크 스타일 제거 */
  .fc .fc-col-header-cell-cushion {
    color: inherit !important;
    text-decoration: none !important;
  }

  /* --- 요일 헤더 색상 (일/토) --- */
  /* 일요일 */
  .fc .fc-col-header-cell.fc-day-sun .fc-col-header-cell-cushion {
    color: red !important;
  }

  /* 토요일 */
  .fc .fc-col-header-cell.fc-day-sat .fc-col-header-cell-cushion {
    color: #0022ff !important; /* 파란색으로 */
  }

  /* --- 날짜 숫자 색상 (토/일) --- */
  .fc-daygrid-day.fc-day-sat .fc-daygrid-day-number {
    color: #0022ff !important;
  }

  .fc-daygrid-day.fc-day-sun .fc-daygrid-day-number {
    color: red !important;
  }

  /* --- 오늘 날짜 강조 --- */
  .fc-day-today {
    background-color: #eaeaea !important;
    /*border: 2px solid rgb(236, 187, 0) !important;*/
  }

</style>

<!-- Page content-->
<div class="container-xl">
  <div class="row py-5">

    <!-- sidebar -->
    <div class="col-lg-2">
      <%@ include file="/WEB-INF/jsp/egovframework/home/pg/common/sidebar.jsp" %>
    </div>

    <!-- Blog entries-->
    <div class="col-lg-10">
      <div id='calendar'></div>
    </div>

  </div>

</div>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.19/index.global.min.js'></script>
<!-- fullcalender docs = https://fullcalendar.io/docs -->
<script>
// fullcalender Started Bundle
document.addEventListener('DOMContentLoaded', function() {
  let calendarEl = document.getElementById('calendar');

  let calendar = new FullCalendar.Calendar(calendarEl, {
    // 언어/시간
    locale: 'ko',
    // timezone: 'local', // 사용자의 브라우저 시간대에 맞춤
    timezone: 'Asia/Seoul',

    // 헤더 툴바
    headerToolbar: {
      left: 'prevYear,prev,next,nextYear today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay'
    },

    // 선택 관련
    selectable: true,
    selectMirror: true,
    selectConstraint: {
      startTime: '06:00',
      endTime: '22:00'
    },

    // 업무 시간대 설정
    slotMinTime: '06:00:00',  // 8시부터
    slotMaxTime: '22:30:00',  // 19시까지만 보이게
    scrollTime: '06:00:00',   // 처음 열 때도 8시 위치로 스크롤
    allDaySlot: false,

    // 버튼
    buttonText: {
      today: '오늘',
      month: '월간',
      week: '주간',
      day: '일간'
    },

    // events: "/getReservationList.do"


  });
  calendar.render();
});

</script>

<jsp:include page="/WEB-INF/jsp/egovframework/home/pg/common/footer.jsp"/>