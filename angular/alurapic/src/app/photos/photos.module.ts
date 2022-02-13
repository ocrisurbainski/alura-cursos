import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { PhotoFormModule } from './photo-form/photo-form.module';
import { PhotoListModule } from './photo-list/photo-list.module';
import { PhotoModule } from './photo/photo.module';
import { PhotosDetailsModule } from './photo-details/photo-details.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        HttpClientModule,
        PhotoModule,
        PhotoFormModule,
        PhotoListModule,
        PhotosDetailsModule,
    ],
    declarations: [
    ]
})
export class PhotosModule { }
