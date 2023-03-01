export class AppSettings {
  public static API_ENDPOINT = 'http://localhost:8080/api/v1';

  public static handleUserAuthenticated(path: string): void {
    if (localStorage.getItem('userToken') != null) {
      window.location.href = path;
    }
  }
}
