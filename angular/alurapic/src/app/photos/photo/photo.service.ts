import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/core/user/user.service';
import { environment } from './../../../environments/environment';
import { Photo } from './photo';
import { PhotoComment } from './photo-comment';

@Injectable({
    providedIn: 'root'
})
export class PhotoService {

    constructor(
        private http: HttpClient,
        private userService: UserService) { }

    listFromUser(username: string, page: number): Observable<Photo[]> {
        const params = new HttpParams()
            .append('page', page.toString());
        return this.http.get<Photo[]>(`${environment.apiUrl}${username}/photos`, { params });
    }

    upload(description: string, allowComments: boolean, fileBase64: string): Observable<any> {
        const headers = new HttpHeaders({ "Content-Type": "application/json"});
        const dados = {
            userId: this.userService.getUserId(),
            url: fileBase64,
            description,
            allowComments,
        };
        return this.http.post(`${environment.apiUrl}photos/upload`, JSON.stringify(dados), {headers});
    }

    findById(id: number): Observable<Photo> {
        return this.http.get<Photo>(`${environment.apiUrl}photos/${id}`);
    }

    getCommentsByIdPhoto(id: number): Observable<PhotoComment[]> {
        return this.http.get<PhotoComment[]>(`${environment.apiUrl}photos/${id}/comments`);
    }

    addComment(id: number, comment: string): Observable<void> {
        const headers = new HttpHeaders({ "Content-Type": "application/json"});
        const dados = { 
            text: comment, 
            userId: this.userService.getUserId(),
            userName: this.userService.getUserName(),
            date: new Date()
        };
        return this.http.post<void>(`${environment.apiUrl}photos/${id}/comments`, JSON.stringify(dados), {headers});
    }

}
