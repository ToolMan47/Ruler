# Ruler
 Side Project with Spring Security  
 練習 Spring Security 配置及保護，JWT產生及驗證，OAuth2.0協議

## 功能
- Username/Password Login
- Google Login
- Facebook Login
- JWT REST API

## Spring
SpringBoot3 當作應用程式框架  
Spring-Data-JPA 當作 ORM，映射資料庫  
Thmyleaf 顯示資料   

## Note
紀錄一些專案過程的心得
1. 配置 Spring Security 後，預設所有路徑都會被管理，無 authentication 會被導入到 認證相關頁面 eg./login
2. 路徑說明: HttpRequest -> Authentication Filter Chain-> Authentication Manager -> Authentication Provider  
 `HttpRequest` : 外界發送的請求，會被 filter攔截  
 `Authentication Filter` : Spring Security 的核心功能。有多層filter，會過濾各種請求，由 Manager 返回 authentication   
 `Authentication Manager` : 此元件會去呼叫登記在他底下的 Provider 找到對應要負責的 Provider (有support的)  
 `Authtencation Provider` : 實作 authenticate 返回 Authentication，並記錄在 security context 中，有support的認證類型會回 true  
`當 Authentication isAuthenticated 且 set in security context 中，即通過認證`

