package com.study.projectjeju.controllers;

import com.study.projectjeju.enums.LoginResult;
import com.study.projectjeju.enums.RegisterResult;
import com.study.projectjeju.services.LoginService;
import com.study.projectjeju.services.RegisterService;
import com.study.projectjeju.vos.LoginVo;
import com.study.projectjeju.vos.RegisterVo;
import com.study.projectjeju.vos.UserVo;
import com.study.utility.Converter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/")
public class UserController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @Autowired
    public UserController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam(name = "email", defaultValue = "") String email,
                      @RequestParam(name = "password", defaultValue = "") String password) throws SQLException, IOException {
        LoginVo loginVo = new LoginVo(email, password);
        LoginResult loginResult;
        if (loginVo.isNormalized()) {
            loginResult = this.loginService.login(request.getSession(), loginVo);
        } else {
            loginResult = LoginResult.NORMALIZATION_FAILURE;
        }

        response.getWriter().print(loginResult.name());
        response.getWriter().close();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("UserVo", null);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(name = "email", defaultValue = "") String email,
                         @RequestParam(name = "password", defaultValue = "") String password,
                         @RequestParam(name = "name", defaultValue = "") String name,
                         @RequestParam(name = "nickname", defaultValue = "") String nickname,
                         @RequestParam(name = "contact", defaultValue = "") String contact,
                         @RequestParam(name = "birth", defaultValue = "") String birth) throws SQLException, IOException {
        String basicImage = "data:image/png;base64,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Ym9vbAAAAAAATGJsc2Jvb2wAAAAAAE5ndHZib29sAAAAAABFbWxEYm9vbAAAAAAASW50cmJvb2wAAAAAAEJja2dPYmpjAAAAAQAAAAAAAFJHQkMAAAADAAAAAFJkICBkb3ViQG/gAAAAAAAAAAAAR3JuIGRvdWJAb+AAAAAAAAAAAABCbCAgZG91YkBv4AAAAAAAAAAAAEJyZFRVbnRGI1JsdAAAAAAAAAAAAAAAAEJsZCBVbnRGI1JsdAAAAAAAAAAAAAAAAFJzbHRVbnRGI1B4bEBSAAAAAAAAAAAACnZlY3RvckRhdGFib29sAQAAAABQZ1BzZW51bQAAAABQZ1BzAAAAAFBnUEMAAAAATGVmdFVudEYjUmx0AAAAAAAAAAAAAAAAVG9wIFVudEYjUmx0AAAAAAAAAAAAAAAAU2NsIFVudEYjUHJjQFkAAAAAAAAAAAAQY3JvcFdoZW5QcmludGluZ2Jvb2wAAAAADmNyb3BSZWN0Qm90dG9tbG9uZwAAAAAAAAAMY3JvcFJlY3RMZWZ0bG9uZwAAAAAAAAANY3JvcFJlY3RSaWdodGxvbmcAAAAAAAAAC2Nyb3BSZWN0VG9wbG9uZwAAAAAAOEJJTQPtAAAAAAAQAEgAAAABAAIASAAAAAEAAjhCSU0EJgAAAAAADgAAAAAAAAAAAAA/gAAAOEJJTQQNAAAAAAAEAAAAHjhCSU0EGQAAAAAABAAAAB44QklNA/MAAAAAAAkAAAAAAAAAAAEAOEJJTScQAAAAAAAKAAEAAAAAAAAAAjhCSU0D9QAAAAAASAAvZmYAAQBsZmYABgAAAAAAAQAvZmYAAQChmZoABgAAAAAAAQAyAAAAAQBaAAAABgAAAAAAAQA1AAAAAQAtAAAABgAAAAAAAThCSU0D+AAAAAAAcAAA/////////////////////////////wPoAAAAAP////////////////////////////8D6AAAAAD/////////////////////////////A+gAAAAA/////////////////////////////wPoAAA4QklNBAAAAAAAAAIAADhCSU0EAgAAAAAAAgAAOEJJTQQwAAAAAAABAQA4QklNBC0AAAAAAAYAAQAAAAM4QklNBAgAAAAAABAAAAABAAACQAAAAkAAAAAAOEJJTQQeAAAAAAAE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Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIwLTExLTEyVDE0OjQ1OjM3KzA5OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMC0xMS0xOVQyMzozOToyMyswOTowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAyMC0xMS0xOVQyMzozOToyMyswOTowMCIgZGM6Zm9ybWF0PSJpbWFnZS9qcGVnIiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBwaG90b3Nob3A6SUNDUHJvZmlsZT0ic1JHQiBJRUM2MTk2Ni0yLjEiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NzUzQzg4RDI3NDJBRUIxMUJEMzRENDIzMEUwRDExM0EiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NzMzQzg4RDI3NDJBRUIxMUJEMzRENDIzMEUwRDExM0EiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo3MzNDODhEMjc0MkFFQjExQkQzNEQ0MjMwRTBEMTEzQSI+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NzMzQzg4RDI3NDJBRUIxMUJEMzRENDIzMEUwRDExM0EiIHN0RXZ0OndoZW49IjIwMjAtMTEtMTJUMTQ6NDU6MzcrMDk6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo3NDNDODhEMjc0MkFFQjExQkQzNEQ0MjMwRTBEMTEzQSIgc3RFdnQ6d2hlbj0iMjAyMC0xMS0xOVQyMzozOToyMyswOTowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNvbnZlcnRlZCIgc3RFdnQ6cGFyYW1ldGVycz0iZnJvbSBpbWFnZS9wbmcgdG8gaW1hZ2UvanBlZyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iZGVyaXZlZCIgc3RFdnQ6cGFyYW1ldGVycz0iY29udmVydGVkIGZyb20gaW1hZ2UvcG5nIHRvIGltYWdlL2pwZWciLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjc1M0M4OEQyNzQyQUVCMTFCRDM0RDQyMzBFMEQxMTNBIiBzdEV2dDp3aGVuPSIyMDIwLTExLTE5VDIzOjM5OjIzKzA5OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgQ1M2IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NzQzQzg4RDI3NDJBRUIxMUJEMzRENDIzMEUwRDExM0EiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NzMzQzg4RDI3NDJBRUIxMUJEMzRENDIzMEUwRDExM0EiIHN0UmVmOm9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo3MzNDODhEMjc0MkFFQjExQkQzNEQ0MjMwRTBEMTEzQSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg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OVNbKSwaPT7r3V03v3Xuve/de6qC/nU/zEqz+Xf8RK3dWw5qI999u5h+tekYq+ljr6TDZmWhlyO5d/ZDHzOkVZQbGwETTRxPqjlydRRxSK0Ujj37r3XzNNxbh3Bu/cOd3du7O5jdO7N05ev3Bufc+4chU5bP7iz2VqHqsnmc1la2SWsyOTr6mQvLNIzMzH+lh7917pn9+691737r3R/P5b38wLs/+XP8j9udx7Nr8pX9d5WtxeG7160p5ycb2P1ytajZOJaGWRaNN57epZJanB15Hlpam8RJpqioik917r6n2y94bc7D2dtTf2z8nT5vae99t4Tdu2MxSNrpsrt/ceNpsvh8jAw+sNZj6yORf8G9+690pvfuvde9+691pw/zYuqd0fylP5iXSX82f45YOam6n7c3pUbV+TGx8JSSRYep3HnlFRv2mrIomShij7o2rBUZKj1jRT7wwv3jEy1Ea+/de62vf9PfU3+gb/Zmv7447/Qj/or/ANNP9+v3f4b/AKN/7r/3y/vF49H3Pj/u9+94tHmv6NOv0+/de6//0z1duU//AA57/wAKNtndSVtTVbi+Pv8AL6w8WczeOhplkww3P1vUYXc+56evjld4Jf493bmsJha1iNU9HhGiAsur37r3W4d7917r3v3Xuve/de60Hv8AhVZ2Bns382uiutqirm/uvsD440u5MRjjIxp0z/YXYG7KbcOSWPUUE9VjtkYyEtYHTTj37r3WsB7917r3v3Xuve/de697917r6X//AAn13xmt8/yn/jLJnKueuqNpf6TOv6GoqXMkowWze0d34fb1HrJJMOLwkMFLEP7MMCr+PfuvdXQ+/de697917om/8wP4t4b5m/Djvz465Wm89dvvYeSk2ZUK8UUuL7H2549zddZWKeYGOIUW88TRNLcgPTmSMkK59+691p8dEfKPfPaH/CcD5xfGKryWeXuP4v7+6b6jpMAZGTOP1p2/8lurqDBbJQIwrKinkyFTuTazwNfRSxJT/oKr7917r//Usk/4S7YCp7Jq/wCYT8xs/jo3znc/eeMwlBmJQZKqNKibc/a+7MdDO5Z/t5ch2TjjIL+p6Zb30j37r3W2p7917r3v3Xuve/de60h/+FXvx5zOM7X+MvyroYGm2xuvZWX6E3LNGjlMZufamWzG/doNUvp0KdwYXcWXSPn/AJdZB+ov7r3Wov7917r3v3Xuve/de64SSJFHJLIbJEjSObE2RFLMbC5NgPfuvdfUs/k4fHncfxh/ltfFzq7edI2P3nNsqs7C3ZjXAWbE5rtXcOY7GfCVS2GiuwVHuWGiqBzaenf37r3Vm/v3Xuve/de697917rSi+PXU8ew/59nz3+HdfQUce0O+uwupPkJi8O1JCMZUf6Mvkl0D80cXTx0ZAhaBNvPuCm02P6Sbce/de6//1bzv+EvmAp8N/LEStgVBJuL5E9w5SoYKodmx67W2vCJGABcrS7eQC5Nhx9PfuvdbFXv3Xuve/de697917orfzO+I/Vvzj+OfYXxu7dgqU2zvihp3x+fxkdK2f2VuvEVCZDbG9Nty1cU0MOY2/lIUkUMNFRAZaeW8M0in3XuvmKfOT4C/I7+Xz2vV9Y9+7TmpsbV1lYvX3aeGp6ifrftPDU7r4sttTNOpSCv8MiGrxNWYsnQO1pIjGY5ZPde6Jd7917ri7pGrPI6xoouzuwVVH9WZiAB7917rZt/ke/ySt+/I3sHY3yy+U+y8js/4z7LyOL3l19sndND9lm+/9xY2rgyG36mfAZCnknpuoKCqpxUVc9UkL5xligplkpHnnHuvdb+oAAAAsBwAOAAPoAPfuvde9+691737r3Xvfuvdao+/6VMP/wAKu+kqilRI23V8ba2TIlBpaZoPj53dTrJKQBrk0bYplub+lF/oPfuvdf/WuZ/4St76pdw/y7d8bOM4OW6/+Se+oa2kLAyU1Hu7aWwt1ULslrok1VXVYFybtG39Le/de62X/fuvde9+691737r3XvfuvdITsrq7rfuTZ2Y687Z2HtHsnYufp2psztLe+38Xubb+RiZWUGoxeXpqqlM0WsmOQKJInsyMrAH37r3VNu/v+E5f8qXfGRGRoOkt39dMWZpaHrrt/sbD4qZmLE2xmWz2epKVQW4WnWFAAABYW9+690Pnx5/kp/y0fjNuHC7y6++Mm2s3vfb7ifE7v7QzG5e1MrQVqlWjyVDR75y+Z29jsrA6hoqmmoYZ4W5jZffuvdWoKqqoVQFVQFVVACqoFgABwAB7917rv37r3Xvfuvde9+691737r3WpfX5yk3v/AMKvcKaKWOal6f6AbDZaoWQNFS11X8f85TrFIwFkkbJdw0dLpPPmlA+pt7917r//17BP5CuUj+Jf8yT+Zp/L23VCmEyFdvXIdgdb0k7ywxV+I683XnoKaHFwzqjTDL9adk4PJQkC70lK7XIT37r3W3p7917r3v3Xuve/de6qu+YP853+X18K63I7Z7J7npd7dlYurahr+pumaWHsjfuMq4yVnp9wQY6tptu7RqKY21xZjI0E3NlRjx7917qh3t3/AIVoaMlUUvQfw1kq8RFLKtPne4+0Y8VkK2IEiGV9q7I27nIKAsOWU5iY8249+690UbLf8Kr/AJ4VdUZMP0X8VMNSC4WlqsL2tmpvqSpesHZeJVmCmxtCoNr2Hv3XulNtT/hV78wse6Denxm+OG6IR+s7dynZey6l/UDcSV+c3vAtluLeP68/ix917qxLob/hVr8Z90Glx/yM+PPbPTlbIaeGbcGwMlhe4NppJI6pNVVMBTZO76OkhBLlIcdXy6RZdbWB917rYl+N/wAvfjN8vNqvvL4291bD7bw1MlO2Vh2xmYnz+3XqgTBT7p2pWrR7n2tVy6WCxZCjpnYqbA2Pv3XujH+/de697917qPV1dLQUlVX11TBR0VFTzVdZV1UqQU1LS00bTVFTUTyMscMEESFndiFVQSTb37r3WhT8Ru9aLLfIP+bH/N7rIsk/WWyPkX8WsPgdwTOPMvXO/wD57dI1m4qWjlYaWlo+jdh49JkX/N0tcqHiQE+691//0LAv51+C3T/L3/mY/Df+bTsLH5ufZO4Mth+sPkAmLpDVQSjb2PnweTxcx9MQrew+k8lkKahjkIH323I3VtZUD3XuttHZe8dtdh7P2rv7ZeXpNwbP3vtzCbu2rnqBzJRZrbm48bTZjC5WkchWamyGNrI5UJAOlxcA+/de6h9hdhbI6n2PursrsrdOF2TsLZGEr9ybs3ZuKtix2GwOExkLVFbX19XMQqRxxrZVF3kchEVnZVPuvdaBX81H/hQR3Z8tMpujpj4n5jcXRvxi8lZhqvdGMkqcD293PjyyRyV2Xy8RiyvXmz65Y28OKoJIa+pp5CK+crIaOL3XutcSwBY8lnd5JGJJaSSRi8kkjG7PJI5JZiSWJueffuvdd+/de697917r3v3Xuve/de6ETqjtztHonf2C7S6Z3/unrHsPbVVFV4bduz8rUYnKQNC4k+1qvETS5bFVBGmehrI6ijqYyUlidSQfde63vf5N/wDPswXzHyWB+M3yyfbuwvk9UxR0Wxd64+OLC7E75mp4AZKGnoW0UWzuz5QhcYqNzR5U6moBG4+zX3Xutl/37r3VKX8+35tUPw6+AnYeLwmcgx/cHyMo8l0p1fRKqzZBKPcFEIOzN109Py6Q7T2HV1PjqACIsnWUK/qlX37r3RCdofyvd/bJ/wCE4HanxowuFrIu+u4NgYv5Q7v2u2MkGbqd4YfeXX/c9D1atCkS175yHY3W9BttYXDSLkdYAF9Pv3Xuv//R3SPnB8Sdh/OH4wdrfGvsFUp6DfuAkG3Nw+EzVWy9+Yhhk9j71oUV45JJ9t7jpoKh4g6CqpxLTufHK4Puvda+P8h75pb6+PHYm+v5OXzQZ9n9w9L7m3Bj+ga/O1FUtLuPERz1WYy3W2KyWQWKKuxn2kwz2zZlIXJ4KteGEAUkKP7r3VfX/ClL+ZLl+3O5pvgX1VuNouo+la2hr+8ajD1z+DsDuHTBkKPZuWMSqlRg+qITE8lOHaOTPzyeZfLjodPuvdasXv3Xuve/de697917r3v3Xuve/de697917r3v3XupePyGRxGQx+Xw+Rr8PmMRX0WVxGXxVZUY7K4nK42pircblMXkKSSKroMljq2BJoJ4nWSKVFZSGAPv3XuvpMfyef5pm2fl78F8z2V33vTAbc7T+LmLfA/JTcOXqo8bQyYXCYeoyeB7iyGtI4KSh3rtvGzT1fiBiTLUddFEoVY19+691Tt8d8Tuz+ft/NWrflTvXBZel/l//DHLUWP6125m1rqTHbxyOJyT5zZOGloZ4I6eXce/c5S0+591wWL0WFpsfiqhm8sLt7r3W6N7917r/9Lf49+691Q5/Oe/lMVPzY25t75HfGuan2H84ejBQ5nYm5cdWjbdR2dittViZvEbOy24adoJMVu3b2Ug+62vmHcChrGenmZaaoMtP7r3Xzvu1J+yKns/sep7li3PB2/V753TXdqQ72oqjG7yTsLIZmryG7m3TQVUNNUUmdnzdVNJUI0aDW91Gkr7917pB+/de697917r3v3Xuve/de697917r3v3Xuve/de697917qx3+W98SPln86ewN5fF3497j3Ls7qbsZNk1Hyj3Yj1sfXOE2RtLPT5rbVTviKnMUG483R5OaefA4LypJkawFn0U0U08PuvdfS2+J/xY6h+GPQ+xPj10jgFwmydkY/xvVTiOXObr3DV6Ztwb03XkEjjbK7o3NkdVRVTsAoJWKJY4I4ok917oxvv3Xuv/09/j37r3XvfuvdUr/wA0/wDkrdDfzG8bN2Bh6qj6Y+UeIxS0OC7exWJjqcXvCmpBD/D9vdu4KkNLPurGU0EJp6PIJKmUxccn7bzQJ9o/uvdaAvzA+B/yo+Ce95Nk/JPqzL7RinqWh25v3GLLnurd7Ql3EFRtPflHAuIrJ5401mgqTS5WnBAnpYjx7917ooPv3Xuve/de697917r3v3Xuve/de64u6INTsqrcC7EAXYgKBf6libAfk+/de6vg/lt/yEvlP83K/b/YPbGNznxs+NM8kVbUb03biGpOyN/Y6Koh8tB1psPKrBX00ORgLiPO5aGHHRr+7TxV9vEfde639fiz8T+h/hj1DgOkfj1sTHbI2ThEWaqeP/K9w7rzkkUceQ3ZvXcMy/xHc26Ms8YaeqqGNlCxRLFBHFEnuvdGN9+691737r3X/9Tf49+691737r3XvfuvdA539/oD/wBEm8f9md/0V/6EP4cf79f6af7sf6Of4d5F8f8AH/74f7gdP3Gnw+X1ebT4/Xp9+691pC/Lroj/AITW9n5vdNZ0L8491fGDe65KoL0eyOm/kR290a9XHqSaPD4Op6tqIGxNRUDUkmB3HHQop/ZQx2X37r3VG3b3x66E2TJJP1V89+ge78dc+KGLqb5edZbkIAJtJit0fH7I7fUtawtmD9ebe/de6K5U4ykppDGm4cJXAf7uoY9wiE/638Q2/jp+f8Yx7917p2wm2cXl6iGCr7C2NteOVwr1m5IOxnpqZT9ZJ12p13uusKj+kcMjf4e/de6sJ6R+JX8v3NVGMrfkT/Nc602Di3ZZcpgOofi38vOwdwLCLFqSn3Du3pfZGFo6p/p5vsa2KP66JPp7917raL/li7P/AOE42yew8Rhfjn2rsTuH5AR1uObbe7/lTit54fedTnQY0ol6yoe6+vut9jw5161dcK7doDkVlZQHJC+/de62n/fuvde9+691737r3Xvfuvdf/9k=";
        RegisterVo registerVo = new RegisterVo(email, password, name, nickname, contact, birth, basicImage);

        RegisterResult registerResult;
        if (registerVo.isNormalized()) {
            registerResult = this.registerService.register(registerVo);
        } else {
            registerResult = RegisterResult.NORMALIZED_FAILURE;
        }

        response.getWriter().print(registerResult.name());
        response.getWriter().close();
    }

    @RequestMapping(value = "/email-check", method = RequestMethod.POST)
    public void emailCheck(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "email", defaultValue = "") String email) throws SQLException, IOException {
        RegisterResult registerResult;
        if (email.equals("")) {
            registerResult = RegisterResult.EMPTY;
        } else {
            registerResult = this.registerService.emailOverlapCheck(email);
        }

        response.getWriter().print(registerResult.name());
        response.getWriter().close();
    }

    @RequestMapping(value = "/nickname-check", method = RequestMethod.POST)
    public void nicknameCheck(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(name = "nickname", defaultValue = "") String nickname) throws SQLException, IOException {
        RegisterResult registerResult;
        if (nickname.equals("")) {
            registerResult = RegisterResult.EMPTY;
        } else {
            registerResult = this.registerService.nicknameOverlapCheck(nickname);
        }

        response.getWriter().print(registerResult.name());
        response.getWriter().close();
    }

    @RequestMapping(value = "/contact-check", method = RequestMethod.POST)
    public void contactCheck(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam(name = "contact", defaultValue = "") String contact) throws SQLException, IOException {
        RegisterResult registerResult;
        if (contact.equals("")) {
            registerResult = RegisterResult.EMPTY;
        } else {
            registerResult = this.registerService.contactOverlapCheck(contact);
        }

        response.getWriter().print(registerResult.name());
        response.getWriter().close();
    }
}
