import { Injectable } from '@angular/core';
import { HttpInterceptor,
  HttpRequest,
  HttpHandler } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class Interceptor implements HttpInterceptor {

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ` + localStorage.getItem('authToken')
      }
    });
    console.log( localStorage.getItem('authToken'))
    return next.handle(request);
  }

}
