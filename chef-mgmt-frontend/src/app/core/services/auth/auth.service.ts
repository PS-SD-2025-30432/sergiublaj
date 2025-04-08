import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from '../../../feature/authentication/models/login-request.model';
import { UserResponse } from '../../../feature/profile/models/user-response.model';
import { ROUTES } from '../../config/routes.enum';
import { isLocalStorageAvailable } from '../../utils/storage-utils';


@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnInit {

  public userSubject = new BehaviorSubject<any>(this.getUserFromStorage());
  public user$ = this.userSubject.asObservable();

  constructor(
    private http: HttpClient,
    private cookieService: CookieService
  ) { }

  ngOnInit(): void {
    this.userSubject.next(this.getUserFromStorage());
  }

  login(credentials: LoginRequest): Observable<void> {
    return this.http.post<void>(`/v1/${ROUTES.AUTH}/${ROUTES.LOGIN}`, credentials);
  }

  logout(): void {
    this.clearUser();
    window.location.reload();
  }

  setUser(user: UserResponse) {
    if (isLocalStorageAvailable()) {
      localStorage.setItem('loggedUser', JSON.stringify(user));
    }
    this.userSubject.next(user);
  }

  clearUser(): void {
    if (isLocalStorageAvailable()) {
      localStorage.removeItem('loggedUser');
    }
    this.cookieService.delete('jwt-token');
    this.userSubject.next(null);
  }

  private getUserFromStorage() {
    return isLocalStorageAvailable()
      ? JSON.parse(localStorage.getItem('loggedUser') || 'null')
      : null;
  }
}
