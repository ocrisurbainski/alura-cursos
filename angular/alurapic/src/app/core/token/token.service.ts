import { Injectable } from '@angular/core';
import { abstractWindowLocalStorageService } from '../window-local-storage/abstract-window-local-storage-service';

@Injectable({
    providedIn: 'root'
})
export class TokenService extends abstractWindowLocalStorageService {

    private TOKEN_KEY = 'authToken';

    constructor() { 
        super();
    }

    hasToken() {
        return !!this.getToken();
    }

    setToken(token: string | null) {
        this.setItem(this.TOKEN_KEY, token);
    }

    getToken(): string | null {
        return this.getItem(this.TOKEN_KEY);
    }

    removeToken() {
        this.removeItem(this.TOKEN_KEY);
    }

}
