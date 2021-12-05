import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component({
    selector: 'app-photo-list',
    templateUrl: './photo-list.component.html',
})
export class PhotoListComponent implements OnInit {

    photos: Photo[] = [];

    filter: string = '';

    constructor(
        private photoService: PhotoService,
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.loadPhotos();
    }

    private loadPhotos() {
        
        const username = this.activatedRoute.snapshot.params['username'];

        this.photoService.listFromUser(username).subscribe({
            next: (photos) => this.photos = photos,
            error: (err) => console.log(err)
        });
    }

}
