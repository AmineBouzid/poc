import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL_ACCESS = 'http://localhost:8080/access/';
const API_URL_USERS = 'http://localhost:8080/users';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL_ACCESS + 'all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL_ACCESS + 'user', { responseType: 'text' });
  }

  getManagerBoard(): Observable<any> {
    return this.http.get(API_URL_ACCESS + 'manager', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL_ACCESS + 'admin', { responseType: 'text' });
  }

  getRegisterBoard(): Observable<any> {
    return this.http.get(API_URL_ACCESS + 'register', { responseType: 'text' });
  }

  getAllUsers(): Observable<any> {
    return this.http.get(API_URL_USERS + '/all', { responseType: 'json' });
  }

  getAllManagers(): Observable<any> {
    return this.http.get(API_URL_USERS + '/managers', { responseType: 'json' });
  }
}
