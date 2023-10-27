# 간단한 scheduler 만들기 예제

<br/>

1. css
```css
@charset "utf-8";

#table {
  font-size: 12px;
}
#table .thead-th-border {
  border: 1px solid #dee2e6;
}
#table tr {
  position: relative;
}
#table td {
  border: 1px solid #dee2e6;
  padding: 5px 0;
}
.modal {
  display: none;
  z-index: 10;
}
.modal-background {
  display: none;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  z-index: 5;
}
.week-1 {
  background: #cee2fb;
}
.week-2 {
  background: #9ec6f8;
}
.week-3 {
  background: #6da9f8;
}
.week-4 {
  background: #3e8ef5;
}
.week-5 {
  background: #305ad9;
}
.add-absolute {
  position: absolute;
  top: 0;
}
.p-hide {
  display: none;
}
.status-pending {
  border-radius: 1px solid #dee2e6;
  color: #dee2e6;
}
.status-ing {
  border-radius: 1px solid #dee2e6;
  color: #2fb135;
}
.status-complete {
  border-radius: 1px solid #dee2e6;
}
```

<br/>

2. html
```html
<!DOCTYPE html>
<html>
<head>
  <title>Web Admin Tools</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
  <body id="page-top">
    <div id="scheduler-wrap">
      <div id="content-wrap" class="d-flex flex-column">
        <div id="content">
          <div class="container-fluid">
            <div th:fragment="frag_body" class="col-md-12">
              <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">회사</li>
                  <li class="breadcrumb-item">내부업무서비스</li>
                  <li class="breadcrumb-item active" aria-current="page">개발일정관리</li>
                </ol>
              </nav>
              <div class="card shadow mb-4">
                <div class="card-body">
                  <div class="d-flex flex-row justify-content-between mb-4">
                    <div>
                      <form id="date" class="d-flex">
                        <input 
                          id="dateStart" 
                          class="form-control" 
                          name="date-start"
                          type="date"
                          value=""
                          onchange="readBoard();"
                        >
                        <input 
                          id="dateEnd"
                          class="form-control ml-1" 
                          name="date-end"
                          type="date"
                          value=""
                          onchange="readBoard();"
                        >
                      </form>
                    </div>
                    <div>
<!--                  <button  -->
<!--                    id=""  -->
<!--                    class="btn btn-light" -->
<!--                    type="button"  -->
<!--                    onclick="" -->
<!--                  > -->
<!--                    Excel -->
<!--                  </button> -->
                      <button 
                        class="btn btn-outline-secondary" 
                        onclick="showDisplay('일정 등록');"
                      >
                        작성
                      </button>
                    </div>
                  </div>
                  <div>
                    <div class="modal animate__animated animate__pulse" tabindex="-1">
                      <div class="modal-dialog">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title"></h5>
                          </div>
                          <div class="modal-body">
                            <label for="project-name" class="col">프로젝트</label>
                            <div class="col-sm-12">
                              <select id="project-name" class="form-control">
                                <option selected>-</option>
                                <option value="0">롯데손해보험</option>
                                <option value="1">흥국 생명/화재</option>
                                <option value="2">키움에셋플래너</option>
                                <option value="3">현대해상</option>
                                <option value="4">천재교육</option>
                                <option value="5">ADMIN</option>
                                <option value="6">API</option>
                                <option value="7">AWS</option>
                                <option value="8">회사</option>
                                <option value="9">기타</option>
                              </select>
                            </div>
                            <label for="type" class="col">분류</label>
                            <div class="col-sm-12">
                              <select id="type" class="form-control">
                                <option selected>-</option>
                                <option value="유지보수">유지보수</option>
                                <option value="기능개발">기능개발</option>
                                <option value="기타">기타</option>
                              </select>
                            </div>
                            <label for="category" class="col">카테고리</label>
                            <div class="col-sm-12">
                              <select id="category" class="form-control">
                                <option selected>-</option>
                                <option value="publishing">publishing</option>
                                <option value="script">script</option>
                                <option value="front-end">front-end</option>
                                <option value="back-end">back-end</option>
                                <option value="database">database</option>
                                <option value="backup">backup</option>
                                <option value="education">education</option>
                                <option value="cm">cm</option>                            
                                <option value="seo">seo</option>
                                <option value="etc">etc</option>
                              </select>
                            </div>
                            <label for="work-details" class="col">업무 내용</label>
                            <div class="col-sm-12">
                              <input id="work-details" type="text" class="form-control" maxlength="300"/>
                            </div>
                            <label for="duration" class="col">소요 시간</label>
                            <div class="col-sm-12">
                              <input id="duration" type="text" class="form-control" maxlength="50"/>
                            </div>
                            <label for="task-owner" class="col">담당자</label>
                            <div class="col-sm-12">
                              <select id="task-owner" class="form-control">
                                <option selected>-</option>
                                <option value="이진우">이진우</option>
                                <option value="전다솔">전다솔</option>
                                <option value="이진우/전다솔">이진우/전다솔</option>
                              </select>
                            </div>
                            <label for="memo" class="col">비고</label>
                            <div class="col-sm-12">
                              <input id="memo" type="text" class="form-control" maxlength="200"/>
                            </div>
                            <label for="week" class="col">주차</label>
                            <div class="col-sm-12">
                              <select id="week" class="form-control">
                                <option value="0">1주차</option>
                                <option value="1">2주차</option>
                                <option value="2">3주차</option>
                                <option value="3">4주차</option>
                                <option value="4">5주차</option>
                              </select>
                            </div>
                            <label for="status" class="col">상태</label>
                            <div class="col-sm-12">
                              <select id="status" class="form-control">
                                <option value="0">보류</option>
                                <option value="1">진행중</option>
                                <option value="2">완료</option>
                              </select>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button 
                              class="btn btn-outline-secondary" 
                              type="button" 
                              onclick="clearInput();" 
                              data-bs-dismiss="modal"
                            >
                              닫기
                            </button>
                            <button 
                              class="btn btn-outline-secondary"
                              type="button" 
                              onclick="writeBoard();"
                            >
                              등록
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="modal-background"></div>
                  <div style="border-radius: 10px; width: 100%;">
                    <table id="table" class="table table-hover text-center">
                      <colgroup>
                        <col width="30px;">
                        <col width="85px;">
                        <col width="60px;">
                        <col width="75px;">
                        <col width="*">
                        <col width="*">
                        <col width="80px;">
                        <col width="*">
                        <col width="70px;">
                        <col width="30px;">
                        <col width="30px;">
                        <col width="30px;">
                        <col width="30px;">
                        <col width="30px;">
                        <col width="60px;">
                      </colgroup>
                      <thead class="thead-light">
                        <tr>
                          <th colspan="2" class="thead-th-border">프로젝트</th>
                          <th>분류</th>
                          <th>카테고리</th>
                          <th>업무</th>
                          <th>소요시간</th>
                          <th>담당자</th>
                          <th>비고</th>
                          <th>날짜</th>
                          <th class="thead-th-border">1 주차</th>
                          <th class="thead-th-border">2 주차</th>
                          <th class="thead-th-border">3 주차</th>
                          <th class="thead-th-border">4 주차</th>
                          <th class="thead-th-border">5 주차</th>
                          <th>STATUS</th>
                        </tr>
                      </thead>
                      <tbody id="create-table-row"></tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
```

<br/>

3. javascript
```javascript
/* IIFE */ 
(() => { 
  todaySet(); 
  readBoard(); 
})(); 

/* Global variable */ 
let statusNumber = '';
/* Function */
function todaySet() {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  const start = document.querySelector('input[name="date-start"]');
  const end = document.querySelector('input[name="date-end"]');
  
  start.value = `${year}-${month}-01`;
  end.value = `${year}-${month}-${day}`;
}

function timeSet(time) {
  const formattedDateTime = time.split(' ')[0];
  return formattedDateTime;
}

function clearInput() {
  window.location.reload(); 
} 

function showDisplay(name) {
  const modalName = document.querySelector('.modal-title');
  modalName.textContent = name;
  
  document.querySelector('.modal').style.display = 'block';
  document.querySelector('.modal-background').style.display = 'block';
}

function readBoard() {
  const start = document.querySelector('input[name="date-start"]');
  const end = document.querySelector('input[name="date-end"]');
  
  (async () => {
    try {
      const res = await axios.get('/scheduler/select', {
        params: {
          start: `${start.value} 00:00:00`,
          end: `${end.value} 23:59:59`
        },
        timeout: 5000, // override the default timeout for this request
        headers: {
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
        },
      });
      
      if (res.status === 200) {
        const data = res.data;
        return createTable(data);
      }
    } catch (err) {
      console.log('err => ', err);
      alert('서버와 통신하는 동안 오류가 발생했습니다.');
    }
  })();
}

function writeBoard() {
  const projectName = document.querySelector('#project-name').value;
  const type = document.querySelector('#type').value;
  const category = document.querySelector('#category').value;
  const workDetails = document.querySelector('#work-details').value;
  const duration = document.querySelector('#duration').value;
  const taskOwner = document.querySelector('#task-owner').value;
  const memo = document.querySelector('#memo').value;
  const week = document.querySelector('#week').value;
  const status = document.querySelector('#status').value;

  const data = {
    projectName,
    type,
    category,
    workDetails,
    duration,
    taskOwner,
    memo,
    week,
    status
  };
  
  (async () => {
    try {
      const res = await axios.post('/scheduler/insert', data, {
        timeout: 5000, // override the default timeout for this request
        headers: {
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
        },
      });
      
      if (res.status === 200) {
        if (res.data === 1) {
          alert('데이터가 정상적으로 입력되었습니다.');
          return window.location.reload();
        } else {
          alert('서버와 통신하는 동안 오류가 발생했습니다.');
        }
      }
    } catch (err) {
      alert('서버와 통신하는 동안 오류가 발생했습니다.');
    }
  })();
}

function createTable(data) {
  let body = '';
  const tableBody = document.querySelector('#create-table-row');
  
  data.forEach(rowData => {
    const row = [
      `<tr data-seq="${rowData.seq}" ondblclick="detail(this);">`,
        tdContent(projectName(rowData.project_name)),
        tdContent(rowData.type),
        tdContent(rowData.category),
        tdContent(rowData.work_details),
        tdContent(rowData.duration),
        tdContent(rowData.task_owner),
        tdContent(rowData.memo),
        tdContent(timeSet(rowData.date_time)),
        tdWeek(rowData.week),
        `
          <td 
            class="status-change" 
            onclick="statusSet(${rowData.status}, ${rowData.seq});"
          >
            ${statusText(rowData.status)}
          </td>
        `,
      '</tr>'
    ].join('');
    body += row;
  });
  tableBody.innerHTML = body;
}

function statusText(name) {
  switch (name) {
    case '0': return '<div class="status-pending">보류</div>'
    case '1': return '<div class="status-ing">진행중</div>'
    case '2': return '<div class="status-complete">완료</div>'
  }
}

function statusSet(status, seq) {
  let parseStatus = parseInt(status);
  if (parseStatus < 2) {
    parseStatus++;
  } else {
    parseStatus = 0;
  }
  return statusApi(parseStatus, seq);
}

function statusApi(status, seq) {
  (async () => {
    try {
      const res = await axios.post('/scheduler/update/two', { status, seq }, {
        timeout: 5000, // override the default timeout for this request
        headers: {
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
        },
      });
      if (res.status === 200) {
        clearInput();
      }
    } catch (err) {
      console.log('err => ', err);
      alert('서버와 통신하는 동안 오류가 발생했습니다.');
    }
  })();
}

function tdContent(data) {
  return `<td>${data}</td>`;
}

function tdWeek(num) {
  const select = [
    [
      '<td class="week-1"></td>',
      '<td></td>',
      '<td></td>',
      '<td></td>',
      '<td></td>',
    ],
    [
      '<td></td>',
      '<td class="week-2"></td>',
      '<td></td>',
      '<td></td>',
      '<td></td>',
    ],
    [
      '<td></td>',
      '<td></td>',
      '<td class="week-3"></td>',
      '<td></td>',
      '<td></td>',
    ],
    [
      '<td></td>',
      '<td></td>',
      '<td></td>',
      '<td class="week-4"></td>',
      '<td></td>',
    ],
    [
      '<td></td>',
      '<td></td>',
      '<td></td>',
      '<td></td>',
      '<td class="week-5"></td>',
    ],
  ];
  return select[num].join('');
};

function projectName(name) {
  switch (name) {
    case '0': return '<img alt="icon" src="/img/icon/lotte.png"></td><td>롯데손해보험';
    case '1': return '<img alt="icon" src="/img/icon/heungkuk.png"></td><td>흥국생명/화재';
    case '2': return '<img alt="icon" src="/img/icon/kiwoom.png"></td><td>키움에셋플래너';
    case '3': return '<img alt="icon" src="/img/icon/hi.png"></td><td>현대해상';
    case '4': return '<img alt="icon" src="/img/icon/milkt.png"></td><td>천재교육';
    case '5': return '<img alt="icon" src="/img/icon/mplanit.png"></td><td>admin';
    case '6': return '<img alt="icon" src="/img/icon/mplanit.png"></td><td>api';
    case '7': return '<img alt="icon" src="/img/icon/aws.png"></td><td>aws';
    case '8': return '<img alt="icon" src="/img/icon/mplanit.png"></td><td>회사';
    case '9': return '<img alt="icon" src="/img/icon/etc.png"></td><td>기타';
    default: return "-";
  }
}

function detail(e) {
  const seq = e.getAttribute('data-seq');
  
  (async () => {
    try {
      const res = await axios.get(`/scheduler/select/one?seq=${seq}`, {
        timeout: 5000, // override the default timeout for this request
        headers: {
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
        },
      });
      if (res.status === 200) {
        const data = res.data;
        const button = document.querySelector('.modal-footer');
        
        document.querySelector('#project-name').value = data.project_name;
        document.querySelector('#type').value = data.type;
        document.querySelector('#category').value = data.category;
        document.querySelector('#work-details').value = data.work_details;
        document.querySelector('#duration').value = data.duration;
        document.querySelector('#task-owner').value = data.task_owner;
        document.querySelector('#memo').value = data.memo;
        document.querySelector('#week').value = data.week;
        document.querySelector('#status').value = data.status;
        
        button.innerHTML = `
          <div class="d-flex justify-content-between" style="width: 100%;">
            <div>
              <button 
                class="btn btn-outline-secondary mr-1" 
                type="button" 
                onclick="deleteTable(${seq});"
              >
                삭제
              </button>
            </div>
            <div>
              <button 
                class="btn btn-outline-secondary mr-1" 
                type="button" 
                onclick="clearInput();" 
                data-bs-dismiss="modal"
              >
                닫기
              </button>
              <button 
                class="btn btn-outline-secondary btn-change ml-1" 
                type="button" 
                onclick="updateTable(${seq});"
              >
                업데이트
              </button>
            </div>
          </div>
        `;        
        return showDisplay('일정 상세');
      }
    } catch (err) {
      console.log('err => ', err);
      alert('서버와 통신하는 동안 오류가 발생했습니다.');
    }
  })();
}

function deleteTable(seq) {
  const select = confirm('삭제하시겠습니까?');
  if (select) {
    (async () => {
      try {
        const res = await axios.post('/scheduler/delete/one', {seq}, {
          timeout: 5000, // override the default timeout for this request
          headers: {
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
          },
        });
        if (res.status === 200) {
          alert('삭제하였습니다.');
          clearInput();
        }
      } catch (err) {
        console.log('err => ', err);
        alert('서버와 통신하는 동안 오류가 발생했습니다.');
      }
    })();
  } else {
    alert('취소하였습니다.');
  }
}

function updateTable(seq) {
  const projectName = document.querySelector('#project-name').value;
  const type = document.querySelector('#type').value;
  const category = document.querySelector('#category').value;
  const workDetails = document.querySelector('#work-details').value;
  const duration = document.querySelector('#duration').value;
  const taskOwner = document.querySelector('#task-owner').value;
  const memo = document.querySelector('#memo').value;
  const week = document.querySelector('#week').value;
  const status = document.querySelector('#status').value;

  const data = {
    projectName,
    type,
    category,
    workDetails,
    duration,
    taskOwner,
    memo,
    week,
    seq,
    status
  };
  
  (async () => {
    try {
      const res = await axios.post('/scheduler/update/one', data, {
        timeout: 5000, // override the default timeout for this request
        headers: {
          'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content,
        },
      });
      if (res.status === 200) {
        alert('업데이트하였습니다.');
        clearInput();
      }
    } catch (err) {
      console.log('err => ', err);
      alert('서버와 통신하는 동안 오류가 발생했습니다.');
    }
  })();
}
```

<br/>

5. controller
```java
@RequestMapping(value = "/dev/scheduler", method = RequestMethod.GET)
public ModelAndView scheduler(HttpServletRequest request, HttpSession httpSession) throws Exception {
  
  if (null == httpSession.getAttribute(Constants.SESS_KEY_PORTAL_MEMBER)) {
    return new RedirectModelAndView("/login.do");
  }
  
  MplModelAndView mv = this.getView("/main/scheduler", request);
  mv.addObject("scheduler", "scheduler");
  return mv;
}
```

<br/>

6. rest controller
```java
  @PostMapping(value = "/scheduler/insert")
  public int insertScheduler(HttpSession httpSession, @RequestBody HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController insertScheduler >>>");
    
    int result = 0;
    {   
      LocalDateTime currentDateTimeInKST = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
      String dateTime = currentDateTimeInKST.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      params.put("dateTime", dateTime);
      result = mainMapper.insertScheduler(params);
    }
    return result;
  }
  
  @GetMapping(value = "/scheduler/select")
  public List<HashMap<String, Object>> selectScheduler(@RequestParam HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController selectScheduler >>>");
    
    return mainMapper.selectScheduler(params);
  }
  
  @GetMapping(value = "/scheduler/select/one")
  public Map<String, Object> selectOneScheduler(@RequestParam HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController selectOneScheduler >>>");
    
    return mainMapper.selectOneScheduler(params);
  }
  
  @PostMapping(value = "/scheduler/delete/one")
  public void deleteOneScheduler(@RequestBody HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController deleteOneScheduler >>>");
    
    mainMapper.deleteOneScheduler(params);
  }
  
  @PostMapping(value = "/scheduler/update/one")
  public Integer updateOneScheduler(@RequestBody HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController updateOneScheduler >>>");
    
    return mainMapper.updateOneScheduler(params);
  }
  
  @PostMapping(value = "/scheduler/update/two")
  public Integer updateTwoScheduler(@RequestBody HashMap<String, Object> params) throws Exception {
    log.info(">>> MainRestController updateTwoScheduler >>>");
    
    return mainMapper.updateTwoScheduler(params);
  }
```

<br/>

7. interface(mapper)
```java
public int insertScheduler(Map<String, Object> params) throws Exception;

public List<HashMap<String, Object>> selectScheduler(Map<String, Object> params) throws Exception;

public Map<String, Object> selectOneScheduler(Map<String, Object> params) throws Exception;

public void deleteOneScheduler(Map<String, Object> params) throws Exception;

public Integer updateOneScheduler(Map<String, Object> params) throws Exception;

public Integer updateTwoScheduler(Map<String, Object> params) throws Exception;
```

<br/>

8. xml
```xml
<insert id="insertScheduler" parameterType="hashmap">
    INSERT INTO scheduler(
      req_time
      , project_name
      , type
      , category
      , work_details
      , duration
      , task_owner
      , memo
      , week
      , status
    )
    VALUES (
      #{dateTime}
      , #{projectName}
      , #{type}
      , #{category}
      , #{workDetails}
      , #{duration}
      , #{taskOwner}
      , #{memo}
      , #{week}
      , #{status}
    )
  </insert>
  
  <select id="selectScheduler" parameterType="hashmap" resultType="hashmap">
    SELECT *, DATE_FORMAT(req_time, '%Y-%m-%d %H:%i:%s') AS date_time
    FROM   scheduler
    WHERE  req_time BETWEEN #{start} AND #{end}
    ORDER  BY project_name, req_time, seq DESC
  </select>
  
  <select id="selectOneScheduler" parameterType="hashmap" resultType="hashmap">
    SELECT *
    FROM   scheduler
    WHERE  seq = #{seq}
  </select>
  
  <delete id="deleteOneScheduler" parameterType="hashmap">
    DELETE
    FROM   scheduler
    WHERE  seq = #{seq}
  </delete>
  
  <update id="updateOneScheduler" parameterType="hashmap">
    UPDATE scheduler
    SET    project_name = #{projectName}
           , type = #{type}
           , category = #{category}
           , work_details = #{workDetails}
           , duration = #{duration}
           , task_owner = #{taskOwner}
           , memo = #{memo}
           , week = #{week}
           , status = #{status}
    WHERE  seq = #{seq}
  </update>
  
  <update id="updateTwoScheduler" parameterType="hashmap">
    UPDATE scheduler
    SET    status = #{status}
    WHERE  seq = #{seq}
  </update>
```

<br/>

10. sql
```sql
CREATE TABLE IF NOT EXISTS `scheduler` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `req_time` datetime DEFAULT NULL,
  `project_name` varchar(20) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `category` varchar(10) DEFAULT NULL,
  `work_details` varchar(300) DEFAULT NULL,
  `duration` varchar(50) DEFAULT NULL,
  `task_owner` varchar(20) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  `week` varchar(1) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
```
