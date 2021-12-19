import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AbstractWindowLocalStorageService } from '../window-local-storage/abstract-window-local-storage-service';
import { User } from './user';

@Injectable({
    providedIn: 'root'
})
export class UserService extends AbstractWindowLocalStorageService {

    private USER_KEY = 'user';
    private userSubject = new BehaviorSubject<User|null>(null);

    constructor() { 
        super();
        this.initUser();
    }

    private initUser() {
        const userJson = this.getItem(this.USER_KEY);
        
        if (userJson) {
            this.userSubject.next(JSON.parse(userJson));
        }
    }

    saveUserLogged(user: User) {
        this.setItem(this.USER_KEY, JSON.stringify(user));
        this.userSubject.next(user);
    }

    getUserLogged(): Observable<User|null> {
        return this.userSubject.asObservable();
    }

    clearUserLogged() {
        this.removeItem(this.USER_KEY);
    }

}
