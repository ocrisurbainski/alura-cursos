import { Injectable } from '@angular/core';
import { abstractWindowLocalStorageService } from '../window-local-storage/abstract-window-local-storage-service';

@Injectable({
    providedIn: 'root'
})
export class UserService extends abstractWindowLocalStorageService {

    private USER_KEY = 'user';

    constructor() { 
        super(); 
    }

    saveUserLogged(user: any) {
        this.setItem(this.USER_KEY, JSON.stringify(user));
    }

    getUserLogged(): any {
        var json = this.getItem(this.USER_KEY);
        if (json) {
            return JSON.parse(json);
        }
        return null;
    }

    clearUserLogged() {
        this.removeItem(this.USER_KEY);
    }

}
