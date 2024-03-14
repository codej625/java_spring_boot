# 각 method 사용 예시

<br />

1) Controller
```java
@Slf4j
@RestController
@RequestMapping(value = "{path}", consumes = "application/json")
public class BabyInsuRestController extends SuperController {

  @Autowired
  RecordMapper mapper;
  
  @PostMapping(value = "/blocked-numbers/add" , consumes = "application/json")
  public ResponseEntity<Void> insert(@RequestBody Map<String, Object> params) throws Exception {
    log.info(">>> insert >>>");
    
    try {
      String content = (String) params.get("content");
      mapper.insert(content);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(value = "/blocked-numbers")
  public Map<String, Object> select(@RequestParam HashMap<String, Integer> params) throws Exception {
    log.info(">>> select >>>");
    
    HashMap<String, Object> result = new HashMap<>();
    result.put("row", mapper.select(params));
    result.put("total", mapper.count());

    return result;
  }

  @PatchMapping(value = "/blocked-numbers", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<Void> update(@RequestBody Map<String, Object> params) {
    log.info(">>> update >>>");
    
    try {
      mapper.update(params);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping(value = "/blocked-numbers/{seq}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<Void> delete(@PathVariable int seq) {
    log.info(">>> delete >>>");
    
    try {
      mapper.delete(seq);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
```

<br />

2) HTML
```html
<div class="card shadow mb-4">
  <div class="card-body">
    <div class="table-responsive">
      <div class="container">
        <div class="row">
          <div class="phone col">
            <table id="table" class="table table-hover table-bordered">
              <colgroup>
                <col width="20%">
                <col width="*">
                <col width="20%">
                <col width="13%">
              </colgroup>
              <thead>
                <tr>
                  <th>등록 날짜</th>
                  <th>차단 번호</th>
                  <th>수정 날짜</th>
                  <th></th>
                </tr>
              </thead>
              <tbody id="phone-tbody">
                
              </tbody>
            </table>
            <div id="pagination" class="pagination"></div>
          </div>
          <div class="phone col-3">
            <form id="frm">
              <table class="table table-hover table-bordered">
                <colgroup>
                  <col width="*">
                  <col width="25%">
                </colgroup>
                <thead>
                  <tr>
                    <th colspan="2">전화번호 등록</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>
                      <input class="form-control" name="phone" type="text" oninput="insertHyphensToPhoneNumber();" placeholder="ex) 010-1234-5678" />
                    </td>
                    <td>
                      <button class="btn btn-outline-secondary add-btn" type="button" onclick="submitCheck();">등록</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
```

<br />

3) Script
```javascript
// IIFE ================================================================================
(() => {

})();
// Global variable =====================================================================
let currentPage = 1;
const pageSize = 10;
const maxPageButtons = 5;
fetchData();
// Function ============================================================================

function fetchData(page = 1) {
  currentPage = page;
  const rowStart = (page - 1) * pageSize + 1;
  const rowEnd = rowStart + pageSize - 1;
  fetch(`/blocked-numbers?rowStart=${rowStart-1}&rowEnd=${rowEnd}`, {
    method: 'get',
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
  })
  .then(response => response.json())
  .then(data => {
    createTable(data.row);
    updatePaginationButtons(data.total, currentPage);
  })
  .catch(error => {
    console.error('Error fetching data:', error);
  });
}
// Function ============================================================================
function createTable(data) {
  const tableBody = document.getElementById('table').getElementsByTagName('tbody')[0];
  tableBody.innerHTML = '';

  data.forEach(item => {
    const row = document.createElement('tr');
    const dateCell = document.createElement('td');
    dateCell.textContent = item.date;
    row.appendChild(dateCell);
  
    const blockedContentCell = document.createElement('td');
    blockedContentCell.textContent = formatPhoneNumber(item.blockedContent);
    row.appendChild(blockedContentCell);
  
    const modifiedDateCell = document.createElement('td');
    modifiedDateCell.textContent = item.modifiedDate;
    row.appendChild(modifiedDateCell);
  
    const buttonCell = document.createElement('td');
    
    const editButton = document.createElement('button');
    editButton.className = "btn btn-outline-secondary mr-2";
    editButton.type = "button";
    editButton.textContent = "수정";
    editButton.addEventListener('click', () => {
      updateBtn(item.seq);
    });
    buttonCell.appendChild(editButton);
  
    const deleteButton = document.createElement('button');
    deleteButton.className = "btn btn-outline-secondary";
    deleteButton.type = "button";
    deleteButton.textContent = "삭제";
    deleteButton.addEventListener('click', () => {
      deletePhone(item.seq);
    });
    buttonCell.appendChild(deleteButton);
  
    row.appendChild(buttonCell);
  
    tableBody.appendChild(row);
  });
}
/* Pagination */
function updatePaginationButtons(total, currentPage) {
  const totalPage = Math.ceil(total / pageSize);
  let startPage = Math.max(1, currentPage - Math.floor(maxPageButtons / 2));
  let endPage = Math.min(totalPage, startPage + maxPageButtons - 1);
  startPage = Math.max(1, endPage - maxPageButtons + 1);

  const paginationContainer = document.getElementById('pagination');
  paginationContainer.innerHTML = '';

  /* first page button */
  const firstPageButton = document.createElement('button');
  firstPageButton.innerText = '<';
  firstPageButton.classList.add('btn', 'btn-outline-secondary', 'mr-1');
  firstPageButton.style.backgroundColor = '#e9ecef';
  firstPageButton.onclick = () => fetchData();
  paginationContainer.appendChild(firstPageButton);
  /* page buttons */
  for (let i = startPage; i <= endPage; i++) {
    const button = document.createElement('button');
    button.innerText = i;
    button.classList.add('btn', 'btn-outline-secondary', 'mr-1');
    button.onclick = () => fetchData(i);
    paginationContainer.appendChild(button);
  }
  /* Add last page button */
  const lastPageButton = document.createElement('button');
  lastPageButton.innerText = '>';
  lastPageButton.classList.add('btn', 'btn-outline-secondary', 'mr-1');
  lastPageButton.style.backgroundColor = '#e9ecef';
  lastPageButton.onclick = () => fetchData(totalPage);
  paginationContainer.appendChild(lastPageButton);
}
// Function ============================================================================
function submitCheck() {
  const phone = document.getElementsByName('phone')[0];
  const strReplace = phone.value.replace(/(\s*)/g, ""); /* Remove whitespace */
  
  if (strReplace === '') {
    alert('전화번호를 입력해주세요.');
    return false;
  } if ((strReplace === null ) || (strReplace === undefined)) {
    alert('전화번호를 입력해주세요.');
    return false;
  } if (strReplace.length !== 13) {
    alert('전화번호를 확인해주세요.');
    return false;
  }
  const result = phone.value.replace(/[^\d]/g, '');
  
  return insertPhone(result);
};
// Function ============================================================================
async function deletePhone(seq) {
  const deleteCheck = confirm('삭제하시겠습니까?');
  
  if (deleteCheck) {
    try {
      /* csrf 처리 */
      const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
      const errorMessage = 'Network error';
      
      const response = await fetch(`/blocked-numbers/${seq}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          [csrfHeader]: csrfToken,
        },
      });

      if (!response.ok) {
        throw new Error(errorMessage);
      }

      alert('삭제되었습니다.');
      fetchData(); // fetchData 함수 호출

    } catch (error) {
      console.error(error);
      alert('Network error');
      /*
        ... 
        Additional logic needs to be added later. 
      */
    }
  } else {
    alert('취소하였습니다.');
  }
}
// Function ============================================================================
async function insertPhone(phone) {
  const deleteCheck = confirm('등록하시겠습니까?');
  
  if (deleteCheck) {
    try {
      /* csrf 처리 */
      const input = document.getElementsByName('phone')[0];
      const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
      const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
      const errorMessage = 'Network error';
      
      const response = await fetch('/blocked-numbers/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          [csrfHeader]: csrfToken,
        },
        body: JSON.stringify({phone})
      })
      
      if (!response.ok) {
        throw new Error(errorMessage);
      }
      
      alert('등록되었습니다.');
      input.value = '';
      
      fetchData();
      
    } catch (error) {
      console.error(error);
      alert('Network error');
      /* Additional logic needs to be added if necessary. */
    }
  } else {
    alert('취소하였습니다.');
  }
}
// Function ============================================================================
async function updatePhone(seq) {
  try {
    /* csrf 처리 */
    const input = document.getElementsByName('update-phone')[0].value;
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const errorMessage = 'Network error';
    
    const response = await fetch('/blocked-numbers', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken,
      },
      body: JSON.stringify({
        phone: input,
        seq
      })
    })
    
    if (!response.ok) {
      throw new Error(errorMessage);
    }
    
    alert('수정되었습니다.');
    input.value = '';
    
    fetchData();
    
  } catch (error) {
    console.error(error);
    alert('Network error');
    /* Additional logic needs to be added if necessary. */
  }
}
// Function ============================================================================
function updateBtn(seq) {
  
  const target = event.target.parentNode.parentNode;
  const targetTd = target.querySelectorAll('td')[1];
  const targetContent = targetTd.textContent;

  if (!targetTd.querySelector('.update-div')) {
    const div = document.createElement('div');
    div.className = 'update-div row';

    const inputDiv = document.createElement('div');
    inputDiv.className = 'col pr-0';

    const input = document.createElement('input');
    input.className = 'form-control';
    input.name = 'update-phone';
    input.type = 'text';
    input.value = targetContent;
    input.style.height = '100%';
    inputDiv.appendChild(input);

    const buttonDiv = document.createElement('div');
    buttonDiv.className = 'col-auto';

    const button = document.createElement('button');
    button.className = 'btn btn-outline-secondary update-btn mr-2';
    button.type = 'button';
    button.textContent = '확인';
    button.style.height = '100%';
    button.addEventListener('click', () => {
      updatePhone(seq);
    });
    buttonDiv.appendChild(button);
    
    const button2 = document.createElement('button');
    button2.className = 'btn btn-outline-secondary update-btn';
    button2.type = 'button';
    button2.textContent = '취소';
    button2.style.height = '100%';
    button2.addEventListener('click', () => {
      targetTd.innerHTML = targetContent;
    });
    buttonDiv.appendChild(button2);

    div.appendChild(inputDiv); 
    div.appendChild(buttonDiv);
    
    targetTd.innerHTML = '';
    targetTd.appendChild(div);
  }
}
// Function ============================================================================
function formatPhoneNumber(phoneNumber) {
  const cleaned = ('' + phoneNumber).replace(/\D/g, '');
  const match = cleaned.match(/^(\d{3})(\d{4})(\d{4})$/);
  
  if (match) {
    return match[1] + '-' + match[2] + '-' + match[3];
  }
  return null;
}
// Function ============================================================================
function insertHyphensToPhoneNumber() {
  const input = document.querySelector('input[name="phone"]');
  let value = input.value.replace(/[^\d]/g, '');
  value = value.replace(/[^0-9]/g, '');

  if (value.length > 3 && value.length <= 7) {
    value = value.slice(0, 3) + '-' + value.slice(3);
  } else if (value.length > 7) {
    value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7);
  }
  input.value = value;
}
```

<br />

4) SQL
```xml
<select id="select" parameterType="hashmap" resultMap="blockResultMap">
  SELECT * 
  FROM block
  WHERE type = 0
  ORDER BY seq DESC
  LIMIT ${rowStart}, 10
</select>

<select id="count" resultType="integer">
  SELECT count(*)
  FROM block
  WHERE type = 0
</select>

<delete id="delete" parameterType="int">
  DELETE 
  FROM block
  WHERE seq = #{seq}
</delete>

<insert id="insert" parameterType="String">
  INSERT 
  INTO block (date, type, blocked_content, modified_date)
  VALUES (now(), 0, #{phone}, now())
</insert>

<update id="update" parameterType="hashmap">
  UPDATE block
  SET blocked_content = #{phone}
      , type = 0
      , modified_date = now()
  WHERE seq = #{seq}
</update>

<resultMap id="blockResultMap" type="kr.co.codej625.model.{company}.BlockDO">
  <id property="seq" column="seq"/>
  <result property="date" column="date"/>
  <result property="blockedContent" column="blocked_content"/>
  <result property="type" column="type"/>
  <result property="modifiedDate" column="modified_date"/>
</resultMap>
```

<br />

5) DB(table)
```sql
CREATE TABLE IF NOT EXISTS `block` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT '고유한 식별자로 사용되는 일련번호',
  `date` datetime NOT NULL COMMENT '데이터가 기록된 날짜/시간',
  `blocked_content` varchar(45) DEFAULT NULL COMMENT '실제로 차단된 내용을 저장하는 컬럼',
  `type` int(1) DEFAULT NULL COMMENT '차단 유형을 구별하기 위한 값',
  `modified_date` datetime NOT NULL COMMENT '데이터가 마지막으로 수정된 날짜/시간을 저장하는 컬럼',
  PRIMARY KEY (`seq`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='- 이 테이블은 차단된 내용을 저장하는데 사용됩니다.\r\n- 각 레코드는 차단된 항목의 세부 정보를 포함합니다.\r\n-- seq: 고유한 식별자\r\n- date: 데이터 기록 날짜\r\n- type: 차단 유형 (0은 IP 차단, 1은 전화번호 차단 등)\r\n- content: 실제 차단된 내용\r\n- modified_date: 데이터가 마지막으로 수정된 날짜';
```
