import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Token} from './token';
import {IAuth} from './i.auth';
import {ERS_JPA_API_URL} from '../../app.constants';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  login(body: IAuth): Observable<Token> {
    return this.http.post<Token>(`${ERS_JPA_API_URL}/api/login`, body);
  }

}
