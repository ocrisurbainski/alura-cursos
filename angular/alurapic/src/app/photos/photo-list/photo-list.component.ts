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

    hasMore: boolean = true;

    page: number = 1;

    userName: string = '';

    constructor(
        private activatedRoute: ActivatedRoute,
        private photoService: PhotoService
    ) { }

    ngOnInit() {
        this.userName = this.activatedRoute.snapshot.params['username'];
        this.photos = this.activatedRoute.snapshot.data['photos'];

        this.setHasMore(this.photos);
    }

    load() {
        this.photoService.listFromUser(this.userName, ++this.page)
            .subscribe((photos) => {
                this.filter = '';
                this.photos = this.photos.concat(photos);
                this.setHasMore(photos);
            });
    }

    private setHasMore(photos: Photo[]): void {
        this.hasMore = photos.length > 0;
    }

}
