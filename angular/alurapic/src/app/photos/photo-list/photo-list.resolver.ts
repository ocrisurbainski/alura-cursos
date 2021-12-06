import { Injectable } from '@angular/core';
import {
    Router, Resolve,
    RouterStateSnapshot,
    ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Photo } from '../photo/photo';

import { PhotoService } from '../photo/photo.service';

@Injectable({
    providedIn: 'root'
})
export class PhotoListResolver implements Resolve<Observable<Photo[]>> {

    constructor(private photoService: PhotoService) {}

    resolve(activatedRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Photo[]> {

        const username = activatedRoute.params['username'];

        return this.photoService.listFromUser(username, 1);
    }

}
