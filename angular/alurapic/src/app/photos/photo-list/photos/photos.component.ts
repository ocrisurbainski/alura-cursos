import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

import { Photo } from '../../photo/photo';

@Component({
    selector: 'app-photos',
    templateUrl: './photos.component.html',
})
export class PhotosComponent implements OnInit, OnChanges {

    @Input()
    photos: Photo[] = [];

    rows: Array<Array<Photo>> = [];

    constructor() { }

    ngOnInit(): void {
        this.rows = this.groupColuns(this.photos);
    }

    ngOnChanges(changes: SimpleChanges) {

        if (changes['photos']) {
            this.rows = this.groupColuns(this.photos);
        }
    }

    private groupColuns(photos: Photo[]): Array<Array<Photo>> {

        const newRows: Array<Array<Photo>> = [];

        for (let index = 0; index < photos.length; index+=3) {
            newRows.push(photos.slice(index, index + 3));
        }

        return newRows;
    }

}
