import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8080/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, password: string, manager: string, nom: string, prenom: string, role: string[]): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password,
      manager,
      nom,
      prenom,
      role
    }, httpOptions);
  }

  updateUser(id: number, username: string, email: string, password: string, manager: string, nom: string, prenom: string, role: string[]): Observable<any> {
    return this.http.put(AUTH_API + 'update', {
      id,
      username,
      email,
      password,
      manager,
      nom,
      prenom,
      role
    }, httpOptions);
  }
}
