import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

import { environment } from 'src/environments/environment';
import { UserService } from '../user/user.service';
import { User } from '../user/user';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(
        private http: HttpClient,
        private userService: UserService) { }

    authenticate(username: string, password: string): Observable<any> {

        const data = {
            username, password
        };

        return this.http
            .post<User>(`${environment.apiUrl}user/login`, data, {observe: 'response'})
            .pipe(tap(res => {
                const authToken = res.headers.get('x-access-token');

                this.userService.saveUserLogged(res.body!, authToken!);
            }));
    }
}
