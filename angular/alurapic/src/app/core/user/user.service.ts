import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TokenService } from '../token/token.service';
import { AbstractWindowLocalStorageService } from '../window-local-storage/abstract-window-local-storage-service';
import { User } from './user';

@Injectable({
    providedIn: 'root'
})
export class UserService extends AbstractWindowLocalStorageService {

    private USER_KEY = 'user';
    private userSubject = new BehaviorSubject<User|null>(null);
    private userName: string= '';

    constructor(
        private tokenService: TokenService) { 
        super();
        this.initUser();
    }

    private initUser() {
        const userJson = this.getItem(this.USER_KEY);
        
        if (userJson) {
            const user = JSON.parse(userJson);
            this.userSubject.next(user);
            this.userName = user.userName;
        }
    }

    saveUserLogged(user: User, token: string) {
        this.tokenService.setToken(token);
        this.setItem(this.USER_KEY, JSON.stringify(user));
        this.userName = user.userName;
        this.userSubject.next(user);
    }

    getUserLogged(): Observable<User|null> {
        return this.userSubject.asObservable();
    }

    logout() {
        this.tokenService.removeToken();
        this.removeItem(this.USER_KEY);
        this.userSubject.next(null);
    }

    isLogged() {
        return this.tokenService.hasToken();
    }

    getUserName() {
        return this.userName;
    }

}
