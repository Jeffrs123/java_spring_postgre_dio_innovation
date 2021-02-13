import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoomService } from 'src/app/services/room.service';
import { Room } from '../room';

@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.scss']
})
export class RoomDetailsComponent implements OnInit {

  id!: number;
  room!: Room;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private roomService: RoomService
  ) { }

  ngOnInit(): void {
    this.room = new Room();
    this.id = this.route.snapshot.params['id'];

    this.roomService
      .getRoom(this.id)
      .subscribe((data: any) => {
        console.log(data);
        this.room = data;
      }, 
      (error: any) => console.log(error))
    ;
  }

  list() {
    this.router.navigate(['rooms']);
  }
}
