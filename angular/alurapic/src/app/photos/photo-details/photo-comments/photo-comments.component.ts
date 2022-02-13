import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, switchMap, tap } from 'rxjs';
import { PhotoComment } from '../../photo/photo-comment';
import { PhotoService } from '../../photo/photo.service';

@Component({
    selector: 'app-photo-comments',
    templateUrl: './photo-comments.component.html',
    styleUrls: ['./photo-comments.component.css']
})
export class PhotoCommentsComponent implements OnInit {

    @Input() photoId: number;

    public comments$: Observable<PhotoComment[]>;

    public formGroup: FormGroup;

    constructor(
        private formBuilder: FormBuilder,
        private photoService: PhotoService) { }

    ngOnInit(): void {
        this.comments$ = this.photoService.getCommentsByIdPhoto(this.photoId);

        this.formGroup = this.formBuilder.group({
            comment: new FormControl('', [Validators.maxLength(300)])
        });
    }

    save():void {
        const value = this.formGroup.getRawValue();
        this.comments$ = this.photoService
            .addComment(this.photoId, value.comment)
            .pipe(switchMap(() => this.photoService.getCommentsByIdPhoto(this.photoId)))
            .pipe(tap(() => {
                this.formGroup.reset();
            }));
    }

}
