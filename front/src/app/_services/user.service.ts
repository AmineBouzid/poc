import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL_ACCESS = 'http://localhost:8080/access/';
const API_URL_USERS = 'http://localhost:8080/users';
const API_URL_PROJECTS = 'http://localhost:8080/project/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

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

  getProjectBoard(): Observable<any> {
    return this.http.get(API_URL_USERS + '/project', { responseType: 'json' });
  }



  getAllUsers(): Observable<any> {
    return this.http.get(API_URL_USERS + '/all', { responseType: 'json' });
  }

  getAllManagers(): Observable<any> {
    return this.http.get(API_URL_USERS + '/managers', { responseType: 'json' });
  }


  addProject(project_name: string, manager_id: number): Observable<any> {
    return this.http.post(API_URL_PROJECTS + 'add', {
      project_name,
      manager_id,
    }, httpOptions);
  }
}
