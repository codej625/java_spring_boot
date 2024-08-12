@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginBusinessLogic loginBusinessLogic;

    @Override
    public void onAuthenticationSuccess
    (
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    )
    throws IOException, ServletException {

        String empId = authentication.getName(); // Origin ID
        String encryptId = ""; // Encrypt ID

        // 1) 로그인 성공 시 로그 업데이트
        loginBusinessLogic.updateUserLastLog(empId);

        // 2) 로그인 성공 시 비밀번호 실패 카운트 리셋
        Map<String, Object> update = new HashMap<String, Object>() {{
            put("username", empId);
            put("count", "reset");
        }};
        loginBusinessLogic.updateFailedLoginCount(update);

        // 3-1) 초기 비밀번호 체크
        boolean checkPassword = loginBusinessLogic.checkPassword(empId);

        // 3-2) 조건에 따른 세션 해제
        if (!checkPassword) {
            request.getSession().invalidate(); // 세션 해제
            encryptId = loginBusinessLogic.encryptId(empId); // 아이디 암호화
        }

        // 4) Redirect Path 생성
        String redirectUrl = loginBusinessLogic.redirectUrl(checkPassword, empId, encryptId);

        response.sendRedirect(request.getContextPath() + redirectUrl);
    }

}
