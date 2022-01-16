import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NumberInput } from '@angular/cdk/coercion';

const API_URL_ACCESS = 'http://localhost:8080/access/';
const API_URL_USERS = 'http://localhost:8080/users/';
const API_URL_PROJECTS = 'http://localhost:8080/project/';
const API_URL_TIME = 'http://localhost:8080/time/';

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
    return this.http.get(API_URL_ACCESS + 'project', { responseType: 'text' });
  }



  getAllUsers(): Observable<any> {
    return this.http.get(API_URL_USERS + 'all', { responseType: 'json' });
  }

  getUserById(user_id: number): Observable<any> {
    let params = new HttpParams().set('id', user_id);
    return this.http.get(API_URL_USERS + 'user/' + user_id, { responseType: 'json' });
  }

  getAllManagers(): Observable<any> {
    return this.http.get(API_URL_USERS + 'managers', { responseType: 'json' });
  }

  getAllProjects(): Observable<any> {
    return this.http.get(API_URL_PROJECTS + 'all', { responseType: 'json' });
  }

  getUserTimes(id: number): Observable<any> {
    return this.http.get(API_URL_TIME + 'usertime/' + id, { responseType: 'json' });
  }




  addProject(project_name: string, manager_id: number): Observable<any> {
    return this.http.post(API_URL_PROJECTS + 'add', {
      project_name,
      manager_id,
    }, httpOptions);
  }

  addTime(date_saisie: string, nb_hours: string, user_id: number, project_id: number): Observable<any> {
    return this.http.post(API_URL_TIME + 'add', {
      date_saisie, nb_hours, user_id, project_id,
    }, httpOptions);
  }

  updateCr(id: number, date_cr: string): Observable<any> {
    return this.http.put(API_URL_USERS + 'cr', {
      id,
      date_cr,
    }, httpOptions);
  }


  deleteUser(user_id: number): Observable<any> {
    return this.http.delete(API_URL_USERS + 'delete/' + user_id, { responseType: 'json' });
  }

  deleteProject(project_id: number): Observable<any> {
    return this.http.delete(API_URL_PROJECTS + 'delete/' + project_id, { responseType: 'json' });
  }

  deleteTime(time_id: number): Observable<any> {
    return this.http.delete(API_URL_TIME + 'delete/' + time_id, { responseType: 'json' });
  }

}
