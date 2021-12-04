import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from './../../../environments/environment';
import { Photo } from './photo';

@Injectable({
    providedIn: 'root'
})
export class PhotoService {

    constructor(private http: HttpClient) { }

    listFromUser(username: string): Observable<Photo[]> {
        return this.http.get<Photo[]>(`${environment.apiUrl}${username}/photos`);
    }

}
