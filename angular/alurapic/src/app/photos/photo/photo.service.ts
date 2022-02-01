import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { Photo } from './photo';

@Injectable({
    providedIn: 'root'
})
export class PhotoService {

    constructor(private http: HttpClient) { }

    listFromUser(username: string, page: number): Observable<Photo[]> {
        const params = new HttpParams()
            .append('page', page.toString());
        return this.http.get<Photo[]>(`${environment.apiUrl}${username}/photos`, { params });
    }

    upload(description: string, allowComments: boolean, fileBase64: string): Observable<any> {
        const formData = new FormData();
        formData.append('description', description);
        formData.append('allowComments', allowComments.toString());
        formData.append('imageFile', fileBase64);
        return this.http.post(`${environment.apiUrl}photos/upload`, formData);
    }

}
