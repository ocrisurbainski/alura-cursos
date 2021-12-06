import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { debounceTime, Subject } from 'rxjs';

import { Photo } from '../photo/photo';

@Component({
    selector: 'app-photo-list',
    templateUrl: './photo-list.component.html',
})
export class PhotoListComponent implements OnInit, OnDestroy {

    photos: Photo[] = [];

    filter: string = '';

    debounce: Subject<string> = new Subject<string>();

    constructor(
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.photos = this.activatedRoute.snapshot.data['photos'];
        this.debounce
            .pipe(debounceTime(500))
            .subscribe(filter => this.filter = filter);
    }

    ngOnDestroy() {
        this.debounce.unsubscribe();
    }

}
