import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Photo } from './photos/photo/photo';
import { PhotoService } from './photos/photo/photo.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
	
    title = "alurapic";

    photos: Photo[] = [];

    constructor(private photoService: PhotoService) { }

    ngOnInit() {
        this.loadPhotos();
    }

    private loadPhotos() {
        this.photoService.listFromUser('flavio').subscribe({
            next: (photos) => this.photos = photos,
            error: (err) => console.log(err)
        });
    }

}
