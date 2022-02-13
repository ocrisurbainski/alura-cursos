import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Photo } from '../photo/photo';
import { PhotoService } from '../photo/photo.service';

@Component({
    selector: 'app-photo-details',
    templateUrl: './photo-details.component.html',
    styleUrls: ['./photo-details.component.css']
})
export class PhotoDetailsComponent implements OnInit {

    public photoId: number;
    public photo$: Observable<Photo>;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private photoService: PhotoService) { }

    ngOnInit(): void {
        this.photoId = this.route.snapshot.params['photoId'];
        this.photo$ = this.photoService.findById(this.photoId);
    }

    remove(): void {
        this.photoService.removePhoto(this.photoId).subscribe(() => this.router.navigate(['']));
    }

}
