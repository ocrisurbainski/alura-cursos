import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, of, throwError } from 'rxjs';
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
        return this.http.post(`${environment.apiUrl}photos/upload`, JSON.stringify(dados), {
            headers, 
            observe: 'events', 
            reportProgress: true
        });
    }

    findById(photoId: number): Observable<Photo> {
        return this.http.get<Photo>(`${environment.apiUrl}photos/${photoId}`);
    }

    getCommentsByIdPhoto(photoId: number): Observable<PhotoComment[]> {
        return this.http.get<PhotoComment[]>(`${environment.apiUrl}photos/${photoId}/comments`);
    }

    addComment(photoId: number, comment: string): Observable<void> {
        const headers = new HttpHeaders({ "Content-Type": "application/json"});
        const dados = { 
            text: comment, 
            userId: this.userService.getUserId(),
            userName: this.userService.getUserName(),
            date: new Date()
        };
        return this.http.post<void>(`${environment.apiUrl}photos/${photoId}/comments`, JSON.stringify(dados), {headers});
    }

    removePhoto(photoId: number): Observable<void> {
        return this.http.delete<void>(`${environment.apiUrl}photos/${photoId}`);
    }

    like(photoId: number): Observable<boolean> {
        const headers = new HttpHeaders({ "Content-Type": "application/json"});
        const dados = {
            userId: this.userService.getUserId()
        };
        return this.http.post(`${environment.apiUrl}photos/${photoId}/like`, JSON.stringify(dados), {headers, observe: 'response'})
            .pipe(map(res => true))
            .pipe(catchError(error => {
                return error.status === '304' ? of(false) : throwError(() => error);
            }));
    }

}
