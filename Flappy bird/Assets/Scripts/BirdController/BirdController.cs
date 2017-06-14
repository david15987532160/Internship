using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BirdController : MonoBehaviour {

    public static BirdController instance; // synchronize between scripts file

    public float bounceForce;

    private Rigidbody2D myBody;
    private Animator anim;

    [SerializeField]
    private AudioSource audioSource;

    [SerializeField]
    private AudioClip flyClip, pingClip, diedClip;

    private bool isAlive;
    private bool didFlap;

    private GameObject spawner;

    public float flag = 0;
    public int score = 0;

    // Use this for initialization
    void Awake() {
        isAlive = true;
        myBody = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        _MakeInstance();
        spawner = GameObject.Find("Spawner pipes");
    }

    void _MakeInstance()
    {
        if(instance == null)
        {
            instance = this; // set instance to the closest class
        }
    }

    // Update is called once per frame
    void FixedUpdate() {
        _BirdMovement();
    }

    void _BirdMovement()
    {
        if(isAlive)
        {
            if (didFlap)
            {
                didFlap = false;
                myBody.velocity = new Vector2(myBody.velocity.x, bounceForce);
                audioSource.PlayOneShot(flyClip);
            }
        }

        /*if (Input.GetMouseButtonDown(0))
        {
            myBody.velocity = new Vector2(myBody.velocity.x, bounceForce);
            audioSource.PlayOneShot(flyClip);
        }*/

        if(myBody.velocity.y > 0)
        {
            float angel = 0;
            angel = Mathf.Lerp(0, 90, myBody.velocity.y / 7);
            transform.rotation = Quaternion.Euler(0, 0, angel);
        }
        else if(myBody.velocity.y == 0)
        {
            transform.rotation = Quaternion.Euler(0, 0, 0);
        }else if (myBody.velocity.y > 0)
        {
            float angel = 0;
            angel = Mathf.Lerp(0, 90, myBody.velocity.y / 7);
            transform.rotation = Quaternion.Euler(0, 0, angel);
        }
    }

    public void FlapButton()
    {
        didFlap = true;
    }

     void OnTriggerEnter2D(Collider2D collision)
     {
        if(collision.tag == "PipeHolder")
        {
            ++score;
            if(GamePlayController.instance != null)
            {
                GamePlayController.instance._SetScore(score);
            }
            audioSource.PlayOneShot(pingClip);
        }  
     }

    void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.gameObject.tag == "Pipe" || collision.gameObject.tag == "Ground")
        {
            flag = 1;
            if (isAlive)
            {
                isAlive = false;
                Destroy(spawner);
                audioSource.PlayOneShot(diedClip);
                anim.SetTrigger("Died");
            }
            if(GamePlayController.instance != null)
            {
                GamePlayController.instance._BirdDiedShowPanel(score);
            }
        }
    }

}
