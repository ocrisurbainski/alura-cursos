import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/core/user/user.service';
import { AlertService } from 'src/app/shared/components/alert/alert.service';
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
        private photoService: PhotoService,
        private alertService: AlertService,
        private userService: UserService) { }

    ngOnInit(): void {
        this.photoId = this.route.snapshot.params['photoId'];
        this.photo$ = this.photoService.findById(this.photoId);
        this.photo$.subscribe({
            error: (err) => {
                console.log(err);
                this.router.navigate(['not-found']);
            }
        })
    }

    remove(): void {
        this.photoService.removePhoto(this.photoId).subscribe({
            next: () => {
                this.alertService.success('Photo removed', true);
                this.router.navigate(['/user', this.userService.getUserName()]);
            },
            error: (err) => {
                console.log(err);
                this.alertService.warning('Could not delete the photo!');
            }
        });
    }

    like(): void {
        this.photoService.like(this.photoId).subscribe(liked => {
            if (liked) {
                this.photo$ = this.photoService.findById(this.photoId);
            }
        })
    }

}
