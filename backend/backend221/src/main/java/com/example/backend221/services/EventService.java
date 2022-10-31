package com.example.backend221.services;

import com.example.backend221.Enum.Role;
import com.example.backend221.component.JwtTokenUtil;
import com.example.backend221.dtos.*;
import com.example.backend221.entities.Event;
import com.example.backend221.entities.EventCategoryOwner;
import com.example.backend221.entities.User;
import com.example.backend221.repositories.EventCategoryOwnerRepository;
import com.example.backend221.repositories.EventCategoryRepository;
import com.example.backend221.repositories.EventRepository;
import com.example.backend221.repositories.UserRepository;
import com.example.backend221.utils.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EventService {

        @Autowired
        private EventRepository repository;
        @Autowired
        private EventCategoryRepository eventCategoryRepository;
        @Autowired
        private ModelMapper modelMapper;
        @Autowired
        private ListMapper listMapper;

        @Autowired
        private JwtTokenUtil jwtTokenUtil;
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private EventRepository eventRepository;
        public EventService() {
        }
        @Autowired
        private EventCategoryOwnerRepository eventCategoryOwnerRepository;

//    public List<SimpleEventDTO> getAllEvent(HttpServletRequest request){
//        String requestTokenHeader = request.getHeader("Authorization");
//        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
//            String header = requestTokenHeader.substring(7);
//            String email = jwtTokenUtil.getUsernameFromToken(header);
//            User user = userRepository.findByEmail(email);
//            Role myRole = user.getRole();
//            System.out.println(myRole.equals((Role.student).toString()));
//            System.out.println(email);
//            if(myRole.equals(Role.student.toString())){
//                return listMapper.mapList(eventRepository.findAllByBookingEmail(email , Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
//            } else if (myRole.equals(Role.admin.toString())){
//                return listMapper.mapList(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
//            } else if(myRole.equals(Role.lecturer.toString())) {
//                int lecturerId = user.getId();
//                return listMapper.mapList(eventRepository.findAllEventByLecturerCategory(lecturerId), SimpleEventDTO.class, modelMapper);
//            }
//        }
//        return listMapper.mapList(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
//    }
    public List<SimpleEventDTO> getAllEvent(HttpServletRequest request) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);
        if (user.getRole().name() == "student") {
            return getEventByEmail(userName);
        }
        if (user.getRole().name() == "lecturer") {
            List<Event> bookingList = eventRepository.findAll(Sort.by("eventStartTime").descending());
            List<EventCategoryOwner> ownerList = eventCategoryOwnerRepository.findByUserID(user.getName());
            List<Event> bookings = new ArrayList<>();
            checkConditionLecturer(bookingList, ownerList, bookings);
            bookings.sort(Comparator.comparing(Event::getEventStartTime).reversed());
            return listMapper.mapList(bookings, SimpleEventDTO.class, modelMapper);
        }
        if (user.getRole().name() == "admin") {
            List<Event> EventList = eventRepository.findAll(Sort.by("eventStartTime").descending());
            return listMapper.mapList(EventList, SimpleEventDTO.class, modelMapper);
        } else {
            List<SimpleEventDTO> emptyEvent = new ArrayList<>();
            return emptyEvent;
        }
    }
        //get all Event with student role

    private List<SimpleEventDTO> getEventByEmail(String user) {
        List<Event> EventList = eventRepository.findEventByBookingEmailOrderByEventStartTimeDesc(user);
        return listMapper.mapList(EventList, SimpleEventDTO.class, modelMapper);
    }


    private void checkConditionLecturer(List<Event> events, List<EventCategoryOwner> owners, List<Event> filter) {
        if (events != null && owners != null) {
            for (EventCategoryOwner owner : owners) {
                for (Event e : events) {
                    if(owner.getEventCategoryID().getId() == e.getEventCategory().getId()) {
                        filter.add(e);
                    }
                }
            }
        }
    }

    //get Event with student role
//    public List<SimpleEventDTO> getEventByEmail(HttpServletRequest request){
//        String requestToken = request.getHeader("Authorization").substring(7);
//        String user = jwtTokenUtil.getUsernameFromToken(requestToken);
//        List<Event> EventList = eventRepository.findEventByBookingEmailOrderByEventStartTimeDesc(user);
//        return  listMapper.mapList(EventList,SimpleEventDTO.class,modelMapper);
//    }
    public List<SimpleEventDTO> getAllEventFilterByEventCategoryAndPassOrFutureOrAll(HttpServletRequest request, Integer eventCategoryId, String pastOrFutureOrAll, String date, int offsetMin, int page, int pageSize) {
        String requestTokenHeader = request.getHeader("Authorization");
        String myRole = "";
        String email = "";
        int lecturerId = 0;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            email = jwtTokenUtil.getUsernameFromToken(header);
            myRole = String.valueOf(userRepository.findByEmail(email).getRole());
            User user = userRepository.findByEmail(email);
            lecturerId = user.getId();
        }
        if (lecturerId != 0) {
            List<Event> event = eventRepository.findAllEventByLecturerCategory(lecturerId);
            return listMapper.mapList(event, SimpleEventDTO.class, modelMapper);
        }

        if (myRole.equals((Role.student).toString())) {
            if (date.equals("")) {
                if (eventCategoryId <= 0) {
                    if (pastOrFutureOrAll.equals("future")) {
                        return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeAfter(email, Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                    } else if (pastOrFutureOrAll.equals("past")) {
                        return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBefore(email, Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                    }
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndIdNot(email, eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }

                if (pastOrFutureOrAll.equals("future")) {
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeAfterAndEventCategoryId(email, Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")) {
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBeforeAndEventCategoryId(email, Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventCategoryId(email, eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            } else {
                //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
                //offsetMin เช่น -420 = +07:00
                Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);
                System.out.println(input);
                long dayInMilli = 86400000;
                if (eventCategoryId > 0) {
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventCategoryIdAndEventStartTimeBetween(email, eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli() + dayInMilli - 1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else {
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBetween(email, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli() + dayInMilli - 1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                }
            }
        }
        //==========================================================

        if (date.equals("")) {
            if (eventCategoryId <= 0) {
                if (pastOrFutureOrAll.equals("future")) {
                    return listMapper.mapList(eventRepository.findAllByEventStartTimeAfter(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")) {
                    return listMapper.mapList(eventRepository.findAllByEventStartTimeBefore(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(eventRepository.findAllByIdNot(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }

            if (pastOrFutureOrAll.equals("future")) {
                return listMapper.mapList(eventRepository.findAllByEventStartTimeAfterAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else if (pastOrFutureOrAll.equals("past")) {
                return listMapper.mapList(eventRepository.findAllByEventStartTimeBeforeAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }
            return listMapper.mapList(eventRepository.findAllByEventCategoryId(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
        } else {
            //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
            //offsetMin เช่น -420 = +07:00
            Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);
            System.out.println(input);
            long dayInMilli = 86400000;
            if (eventCategoryId > 0) {
                return listMapper.mapList(eventRepository.findAllByEventCategoryIdAndEventStartTimeBetween(eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli() + dayInMilli - 1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else {
                return listMapper.mapList(eventRepository.findAllByEventStartTimeBetween(Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli() + dayInMilli - 1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            }
        }
        }

        //delete
        public void deleteEventById(Integer id, HttpServletRequest request) {

            String requestToken = request.getHeader("Authorization").substring(7);
            String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
            User user = userRepository.findByEmail(userName);
            try{
                if ( (user.getRole().name() == "admin") ||
                        (user.getRole().name() == "student" && eventRepository.findEventByIdAndBookingEmail(id,userName).size() != 0) ) {
                    eventRepository.findById(id).orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Can't Found This Booking"));
                    eventRepository.deleteById(id);
                } else eventRepository.findById(null);
            } catch (Exception ex) {
                if(user.getRole().name() == "student"){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The booking email must be the same as the student's email !",ex);
                } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }

        public ResponseEntity getSimpleEventById(Integer id , HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(header);
            Role myRole = userRepository.findByEmail(email).getRole();
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, " id " + id +
                            "Does Not Exist !!!"
                    ));
            if(!myRole.equals((Role.admin).toString())){
                if(!email.equals(event.getBookingEmail())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your Booking Email is not match with your account");
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(event, EventDetailsDTO.class));
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You dont login pls login");
    }
    //create
    public ResponseEntity create(EventDTO newEvent, HttpServletRequest request) throws MessagingException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(header);
            Role myRole = userRepository.findByEmail(email).getRole();
            if(!myRole.equals((Role.admin).toString())){
                if(!email.equals(newEvent.getBookingEmail())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your Booking Email is not match with your account");
                }
            }
        }
        Integer newEventDuration = eventCategoryRepository.findEventCategoryById(newEvent.getEventCategory().getId()).getEventDuration();
        Event e = modelMapper.map(newEvent, Event.class);
        e.setEventDuration(newEventDuration);

        eventRepository.saveAndFlush(e);
        Event event2Send = eventRepository.findById(e.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " +
                        "Does Not Exist !!!"
                ));
        sendmail(event2Send);

        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(e);

    }

//mail
private void sendmail(Event event) throws AddressException, MessagingException, IOException {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            //คือออ
            return new PasswordAuthentication("amornpong.213@gmail.com", "vrplkwnpfhpqhldt");
        }
    });
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm").withZone(ZoneId.of("UTC"));

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress("amornpong.213@gmail.com", false));

    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getBookingEmail()));
    msg.setSubject("Your booking is complete.");
    msg.setContent("Your booking name : " + event.getBookingName() +
                    "<br> Event category : " + event.getEventCategory() +
                    "<br>Start date and time : " + formatter.format(event.getEventStartTime()) +
                    "<br>Event duration : " + event.getEventDuration() + " minitues"+
                    "<br>Event note : " + event.getEventNotes()
            , "text/html; charset=utf-8");
    msg.setSentDate(new Date());

    Transport.send(msg);
}
    //edit
//    public ResponseEntity editEvent(EditEventDTO editEvent , int id , HttpServletRequest request) {
//        Event event = eventRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, " id " + id +
//                        "Does Not Exist !!!"
//                ));
//
//        String requestTokenHeader = request.getHeader("Authorization");
//        String jwtToken = requestTokenHeader.substring(7);
//        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
//        if(!event.getBookingEmail().equals(email)){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deleted Event booking email is not match with your email");
//        }
//        if(!checkTimeFuture(editEvent.getEventStartTime().toEpochMilli())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Time Future Pls");
//        }
//
//        int eventDuration = event.getEventDuration();
//        EventCategory eventCategory = event.getEventCategory();
//        if(!isOverLap(new OverLabDTO(editEvent.getEventStartTime(), eventCategory, eventDuration), id)){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OverLab");
//        }
//
//        event.setEventStartTime(editEvent.getEventStartTime());
//        event.setEventNotes(editEvent.getEventNotes());
//
//        eventRepository.saveAndFlush(event);
//
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(event);
//    }

    public EditEventDTO editEvent(HttpServletRequest request,EditEventDTO editEvent, Integer id) {
        String requestToken = request.getHeader("Authorization").substring(7);
        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
        User user = userRepository.findByEmail(userName);

        if ( user.getRole().name() == "admin" || (user.getRole().name() == "student" && eventRepository.findEventByIdAndBookingEmail(id,userName).size() != 0)) {
            Event event = eventRepository.findById(id).map(o->mapEvent(o, editEvent))
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.FORBIDDEN, "No ID : " + id));
            eventRepository.saveAndFlush(event);
            return modelMapper.map(event, EditEventDTO.class);
        }else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    private Event mapEvent(Event existingEvent, EditEventDTO updateEvent) {
        existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }
    public List<EventCheckDTO> getAllEventForOverLabFront(Integer eventId, Integer categoryId, String startTime){
        if(eventId != 0){
            categoryId = eventRepository.findById(eventId).get().getEventCategory().getId();
            System.out.println(categoryId);
        }
        Instant input = Instant.parse(startTime);
        long maxDuration = 480 *60 *1000;

        return listMapper.mapList(eventRepository.findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(eventId, categoryId, Instant.ofEpochMilli(input.toEpochMilli()-maxDuration-1), Instant.ofEpochMilli(input.toEpochMilli()+maxDuration+1), PageRequest.of( 0, Integer.MAX_VALUE, Sort.by("eventStartTime").descending())), EventCheckDTO.class, modelMapper);
    }
    public boolean checkTimeFuture(long eventStartTime){
        if(eventStartTime+60*1000 > Instant.now().toEpochMilli()) {

            return true;
        }
        return false;
    }


    public Event save (EventDTO newEvent){
            newEvent.setEventDuration(eventCategoryRepository.getById(newEvent.getEventCategory().getId()).getEventDuration());
            Event e = modelMapper.map(newEvent, Event.class);
            return repository.saveAndFlush(e);
        }
        public boolean validateFuture (Instant eventStartTime){
            long milliStartTime = eventStartTime.toEpochMilli(); //แปลงเป็นmilli second
            long realTime = Instant.now().toEpochMilli();
            if (milliStartTime > realTime) {
                return true;
            }
            return false;
        }
        public List<Event> getEventByCategoryId (Integer categoryId){
            return repository.findEventByEventCategory_IdOrderByEventStartTimeDesc(categoryId);
        }
//    private List <Event> getAllByEventCategory_IdAndAndEventStartTime(Integer categoryId, LocalDateTime present,LocalDateTime tomorrow){
//            return repository.findAllByEventCategory_IdAndAndEventStartTime(categoryId,present,tomorrow);
//     }
public boolean isOverLap(OverLabDTO event, int id){
    long newEventStartTimeMilli = event.getEventStartTime().toEpochMilli();
    long newDurationMilli =  eventCategoryRepository.findEventCategoryById(event.getEventCategory().getId()).getEventDuration() * 60 * 1000;

    List<Event> eventList = eventRepository.findAllByEventCategoryIdAndEventStartTimeBetween(event.getEventCategory().getId(), Instant.ofEpochMilli(newEventStartTimeMilli).minus(480, ChronoUnit.MINUTES), Instant.ofEpochMilli(newEventStartTimeMilli).plus(480, ChronoUnit.MINUTES), PageRequest.of(0, Integer.MAX_VALUE));

    for (int i = 0; i < eventList.size(); i++) {
        System.out.println(eventList.size());

        if(id != eventList.get(i).getId()){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
            System.out.println("start Va5");

            long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
            long duration = eventList.get(i).getEventDuration() * 60 * 1000;
            System.out.println("CategoryChecked");

            System.out.println("newstart+newdu"+ newEventStartTimeMilli+newDurationMilli);
            System.out.println(newEventStartTimeMilli+newDurationMilli);

            System.out.println("mill" +milliSecond);
            if(newEventStartTimeMilli+newDurationMilli > milliSecond && newEventStartTimeMilli+newDurationMilli < milliSecond+duration){
                System.out.println("Overlab1+4");
                return false;
            }
            else if (newEventStartTimeMilli > milliSecond && newEventStartTimeMilli < milliSecond+duration){
                System.out.println("Overlab2+4");
                return false;
            }
            else if (newEventStartTimeMilli <= milliSecond && newEventStartTimeMilli+newDurationMilli >= milliSecond){
                System.out.println("Overlab3");
                return false;
            }
        }
    }
    return true;



}

//     public boolean isOverlap(EventDTO eventDTO){
//            boolean isOverlap = false;
//            int eventCategoryId = eventDTO.getEventCategory().getId();
//            LocalDateTime present = LocalDateTime.from(eventDTO.getEventStartTime().minusMillis(eventDTO.getEventStartTime().getEpochSecond()).minusMillis(eventDTO.getEventStartTime().getEpochSecond()));
//            LocalDateTime tomorrow = present.plusDays(2);
//            List<Event> check = getAllByEventCategory_IdAndAndEventStartTime(eventCategoryId,present,tomorrow);
//            if(check ==null) isOverlap = false;
//            else{
//                for(Event event : check){
//                    LocalDateTime start = LocalDateTime.from(event.getEventStartTime());
//                    LocalDateTime end = LocalDateTime.from(event.getEventStartTime().plusMillis(event.getEventDuration().longValue()));
//                    if(eventDTO.getEventStartTime().equals(start) ||
//                            (eventDTO.getEventStartTime().isBefore(Instant.from(end)) && eventDTO.getEventStartTime().isAfter(Instant.from(start))) ||
//                            (eventDTO.getEventStartTime().plusMillis(eventDTO.getEventDuration()).isBefore(Instant.from(end)) &&
//                                    eventDTO.getEventStartTime().plusMillis(event.getEventDuration()).isAfter(Instant.from(start)))
//                    ) {
//                        isOverlap = true;
//                        System.out.println(event);
//                    }
//                }
//            } return isOverlap;
//        }

    }