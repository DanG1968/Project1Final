import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HardcodedAuthenticationService {

  constructor() { }
  authenticate(username, password) {
    // console.log('before ' + this.isUserLoggedIn());
    if (username === 'in28minutes' && password === 'dummy') {
      sessionStorage.setItem('authenticaterUser', username);

      return true;
    }
    return false;
  }

  isUserLoggedIn() {
    const user = localStorage.getItem('authToken');
    return !(user === null);
  }

  logout() {
    localStorage.removeItem('authToken');
  }
}
