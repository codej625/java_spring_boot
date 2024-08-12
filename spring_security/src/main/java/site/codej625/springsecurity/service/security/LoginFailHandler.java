package site.codej625.springsecurity.service.security;

@RequiredArgsConstructor
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final LoginBusinessLogic loginBusinessLogic;
    private final AccountBusinessLogic accountBusinessLogic;

    @Override
    public void onAuthenticationFailure
    (
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    )
    throws IOException, ServletException {
        
        String username = request.getParameter("username");
        // 실패 카운트 업데이트
        Map<String, Object> update = new HashMap<String, Object>() {{
            put("username", username);
            put("count", "up");
        }};
        loginBusinessLogic.updateFailedLoginCount(update);

        Employee employee = loginBusinessLogic.getUserInfo(username);

        // 1) 상태가 활성화 상태이면서
        if (employee.getEnable() == 1) {
            // 1-1) 로그인 실패 카운트가 5 혹은 그 이상인 사용자
            if (employee.getFailCnt() >= 5) {
                // 1-2) 비활성 상태로 업데이트
                Map<String, Object> params = new HashMap<String, Object>() {{
                    put("username", username);
                    put("state", 0);
                }};
                accountBusinessLogic.updateUserState(params);
                // 1-3) 비활성 상태값 전달
                response.sendRedirect(request.getContextPath() + "/login?enable=false");
                return;
            }

            // 2) 활성화 상태이면서 카운트가 5 이하인 사용자
            response.sendRedirect(request.getContextPath() + "/login?check=false");
            return;
        }
        // 3) 상태가 비활성화 상태
        response.sendRedirect(request.getContextPath() + "/login?enable=false");
    }

}
