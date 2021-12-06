import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { debounceTime, Subject } from 'rxjs';

import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component({
    selector: 'app-photo-list',
    templateUrl: './photo-list.component.html',
})
export class PhotoListComponent implements OnInit, OnDestroy {

    photos: Photo[] = [];

    filter: string = '';

    debounce: Subject<string> = new Subject<string>();

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
        this.debounce
            .pipe(debounceTime(500))
            .subscribe(filter => this.filter = filter);

        this.setHasMore(this.photos);
    }

    ngOnDestroy() {
        this.debounce.unsubscribe();
    }

    load() {
        this.photoService.listFromUser(this.userName, ++this.page)
            .subscribe((photos) => {
                this.photos = this.photos.concat(photos);
                this.setHasMore(photos);
            });
    }

    private setHasMore(photos: Photo[]): void {
        this.hasMore = photos.length > 0;
    }

}
